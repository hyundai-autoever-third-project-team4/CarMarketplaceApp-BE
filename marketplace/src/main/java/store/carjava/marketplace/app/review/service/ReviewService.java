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
import store.carjava.marketplace.app.review.exception.ReviewIdNotFoundException;
import store.carjava.marketplace.app.review.exception.ReviewWriterNotMatchException;
import store.carjava.marketplace.app.review.repository.ReviewRepository;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.user.exception.UserIdNotFoundException;
import store.carjava.marketplace.app.user.repository.UserRepository;
import store.carjava.marketplace.common.util.UserResolver;

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

    public ReviewCreateResponse createReview(ReviewCreateRequest request) {

        // 현재 요청하는 사용자 추출
        User currentUser = userResolver.getCurrentUser();

        //차량 존재여부 확인
        MarketplaceCar car = carRepository.findById(request.carId())
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id:" + request.carId()));

        //리뷰 내용 공백 확인
        if (StringUtils.isBlank(request.content())) {
            throw new IllegalArgumentException("리뷰 내용은 필수입니다");
        }

        Review review = Review.builder()
                .marketplaceCar(car)
                .user(currentUser)
                .model(car.getCarDetails().getModel())
                .content(request.content())
                .starRate(request.starRate())
                .createdAt(LocalDateTime.now())
                .build();

        // 리뷰를 저장하고 응답을 반환
        Review savedReview = reviewRepository.save(review);

        return ReviewCreateResponse.of(savedReview);

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
