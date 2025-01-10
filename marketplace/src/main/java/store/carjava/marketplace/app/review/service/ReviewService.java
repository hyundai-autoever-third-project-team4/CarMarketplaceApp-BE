package store.carjava.marketplace.app.review.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.app.review.dto.ReviewCreateRequest;
import store.carjava.marketplace.app.review.dto.ReviewCreateResponse;
import store.carjava.marketplace.app.review.repository.ReviewRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewCreateResponse createReview(Long userId, String carId, ReviewCreateRequest request) {}
}
