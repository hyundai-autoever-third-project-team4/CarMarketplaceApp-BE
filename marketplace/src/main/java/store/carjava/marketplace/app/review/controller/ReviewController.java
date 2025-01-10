package store.carjava.marketplace.app.review.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.carjava.marketplace.app.review.dto.ReviewCreateRequest;
import store.carjava.marketplace.app.review.dto.ReviewCreateResponse;
import store.carjava.marketplace.app.review.dto.ReviewInfoListDto;
import store.carjava.marketplace.app.review.service.ReviewService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Review", description = "구매한 차 리뷰 관련 API")
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(description = "구매할 차량 리뷰작성")
    @PostMapping("/review")
    public ResponseEntity<ReviewCreateResponse> createReview(@RequestBody ReviewCreateRequest request) {
        ReviewCreateResponse response = reviewService.createReview(request);
        return ResponseEntity.ok(response);

    }
//
//    @Operation(description = "마이페이지에서 나의 리뷰 조회 api")
//    @GetMapping("/{userId}/review")
//    public ResponseEntity<ReviewInfoListDto> getUserReviews(
//            @Parameter(description = "회원 ID") @PathVariable Long userId
//    ) {
//        ReviewInfoListDto userReviews = reviewService.getMyReviews();
//        return ResponseEntity.ok(userReviews);
//    }


}
