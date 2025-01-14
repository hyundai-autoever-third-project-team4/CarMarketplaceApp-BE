package store.carjava.marketplace.app.review.service;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.repository.MarketplaceCarRepository;
import store.carjava.marketplace.app.review.dto.*;
import store.carjava.marketplace.app.review.entity.Review;
import store.carjava.marketplace.app.review.exception.ReviewAlreadyExistsException;
import store.carjava.marketplace.app.review.exception.ReviewIdNotFoundException;
import store.carjava.marketplace.app.review.exception.ReviewWriterNotMatchException;
import store.carjava.marketplace.app.review.repository.ReviewRepository;
import store.carjava.marketplace.app.review_image.entity.ReviewImage;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.user.exception.UserIdNotFoundException;
import store.carjava.marketplace.app.user.repository.UserRepository;
import store.carjava.marketplace.common.util.user.UserResolver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MarketplaceCarRepository carRepository;
    private final UserRepository userRepository;

    private final UserResolver userResolver;

    public ReviewCreateResponse createReview(ReviewCreateRequest request, List<String> imageUrls) {

        // 현재 요청하는 사용자 추출
        User currentUser = userResolver.getCurrentUser();

        //차량 존재여부 확인
        MarketplaceCar car = carRepository.findById(request.carId())
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id:" + request.carId()));

        // 해당 사용자가 이미 이 차량에 대한 리뷰를 작성했는지 확인
        if (reviewRepository.existsByUserAndMarketplaceCar(currentUser, car)) {
            throw new ReviewAlreadyExistsException();
        }

        //리뷰 내용 공백 확인
        if (StringUtils.isBlank(request.content())) {
            throw new IllegalArgumentException("리뷰 내용은 필수입니다");
        }

        //리뷰 엔티티 생성.
        Review review = createReviewEntity(request, currentUser, car);

        // 이미지가 있는 경우 ReviewImage 엔티티들을 생성하고 리뷰와 연결
        if (!imageUrls.isEmpty()) {
            addImagesToReview(review, imageUrls);
        }

        // 리뷰를 저장하고 응답을 반환
        Review savedReview = reviewRepository.save(review);
        return ReviewCreateResponse.of(savedReview);

    }

    private void addImagesToReview(Review review, List<String> imageUrls) {

        for (String imageUrl : imageUrls) {
            ReviewImage reviewImage = ReviewImage.builder()
                    .review(review)
                    .imageUrl(imageUrl)
                    .build();
            review.getReviewImages().add(reviewImage);
        }
    }

    private Review createReviewEntity(ReviewCreateRequest request, User currentUser, MarketplaceCar car) {
        return Review.builder()
                .marketplaceCar(car)
                .user(currentUser)
                .model(car.getCarDetails().getModel())
                .content(request.content())
                .starRate(request.starRate())
                .createdAt(LocalDateTime.now())
                .build();
    }

    //마이페이지_본인 작성 리뷰 조회
    public ReviewInfoListDto getMyReviews(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserIdNotFoundException::new);

        List<ReviewInfoDto> reviewInfoList = reviewRepository.findAllByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(ReviewInfoDto::of)
                .collect(Collectors.toList());

        return ReviewInfoListDto.of(reviewInfoList);
    }

    public ReviewDeleteResponse deleteReview(Long reviewId) {

        // 1. 현재 요청하는 사용자 추출
        User currentUser = userResolver.getCurrentUser();

        // 2. 리뷰 존재 여부 확인
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewIdNotFoundException::new);

        // 3. 리뷰 작성자와 요청자가 같은지 확인
        verifyReviewWriter(review.getUser().getId(), currentUser.getId());

        // 4. 리뷰 삭제
        reviewRepository.delete(review);

        // 5. 삭제 결과 반환
        return ReviewDeleteResponse.of(reviewId);

    }

    public ReviewInfoListDto getCarReviews(String carId) {
        // 1. 차량 정보 조회
        MarketplaceCar car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + carId));


        // 2. 차량 모델로 리뷰 조회
        String carModel = car.getCarDetails().getModel();
        List<ReviewInfoDto> reviewInfoList = reviewRepository
                .findTop5ByMarketplaceCar_CarDetails_ModelOrderByCreatedAtDesc(carModel)
                .stream()
                .map(ReviewInfoDto::of)
                .collect(Collectors.toList());

        return ReviewInfoListDto.of(reviewInfoList);

    }

    //리뷰 상세 조회
    public ReviewInfoDto getReviewDetail(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewIdNotFoundException::new);

        return ReviewInfoDto.of(review);
    }

    private void verifyReviewWriter(long writerId, long currentUserId) {
        if (writerId != currentUserId) {
            throw new ReviewWriterNotMatchException();
        }
    }

}
