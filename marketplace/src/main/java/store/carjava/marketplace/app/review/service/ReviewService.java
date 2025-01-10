package store.carjava.marketplace.app.review.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.repository.MarketplaceCarRepository;
import store.carjava.marketplace.app.review.dto.ReviewCreateRequest;
import store.carjava.marketplace.app.review.dto.ReviewCreateResponse;
import store.carjava.marketplace.app.review.dto.ReviewInfoListDto;
import store.carjava.marketplace.app.review.entity.Review;
import store.carjava.marketplace.app.review.repository.ReviewRepository;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.user.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MarketplaceCarRepository carRepository;
    private final UserRepository userRepository;

    public ReviewCreateResponse createReview(ReviewCreateRequest request) {


        //        Member currentMember = memberResolver.getCurrentMember();


        MarketplaceCar car = carRepository.findById(request.getCarId())
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id:"+request.getCarId() ));


        User user = userRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: 1"));


        Review review = Review.builder()
                .marketplaceCar(car)
                .user(user)
                .model(car.getCarDetails().getModel())
                .content(request.getContent())
                .starRate(request.getStarRate())
                .createdAt(LocalDateTime.now())
                .build();

        // 리뷰를 저장하고 응답을 반환합니다
        Review savedReview = reviewRepository.save(review);
        return ReviewCreateResponse.of(savedReview);

    }

    /*public ReviewInfoListDto getMyReviews(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: 1"));
    }*/
}
