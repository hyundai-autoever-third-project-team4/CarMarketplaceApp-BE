package store.carjava.marketplace.app.review.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import store.carjava.marketplace.app.review.dto.ReviewCreateResponse;
import store.carjava.marketplace.app.review.service.ReviewService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Review", description = "산 차 리뷰 관련 API")
public class ReviewController {
    private final ReviewService reviewService;
    public ResponseEntity<ReviewCreateResponse>
}
