package store.carjava.marketplace.app.review.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import store.carjava.marketplace.app.review.dto.*;
import store.carjava.marketplace.app.review.service.ReviewService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Review", description = "구매한 차 리뷰 관련 API")
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(description = "구매할 차량 리뷰작성")
    @PostMapping("/review")
    public ResponseEntity<ReviewCreateResponse> createReview(@Valid @RequestBody ReviewCreateRequest request) {
        ReviewCreateResponse response = reviewService.createReview(request);
        return ResponseEntity.ok(response);

    }

    @Operation(description = "마이페이지에서 나의 리뷰 조회 api")
    @GetMapping("/{userId}/review")
    public ResponseEntity<ReviewInfoListDto> getUserReviews(
            @Parameter(description = "회원 ID") @PathVariable("userId") Long userId
    ) {
        ReviewInfoListDto userReviews = reviewService.getMyReviews(userId);
        return ResponseEntity.ok(userReviews);
    }

    @Operation(description = "마이페이지 리뷰 삭제")
    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<ReviewDeleteResponse> deleteReview(
            @Parameter(description = "삭제할 리뷰 ID") @PathVariable("reviewId") Long reviewId
    ) {
        ReviewDeleteResponse responseDto = reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(responseDto);
    }

    //리뷰controller 말고 차량조회에서 CarDetailResponseDto에 ReviewInfoList도 함께 조회하도록 수정 필요
    @Operation(description = "차량 상세에서 동일모델의 리뷰 조회")
    @GetMapping("/cars/{carId}/review")
    public ResponseEntity<ReviewInfoListDto> getCarReviews(
            @Parameter(description = "carId") @PathVariable("carId") String carId
    ){
        ReviewInfoListDto carReviews = reviewService.getCarReviews(carId);
        return ResponseEntity.ok(carReviews);
    }

    @Operation(description = "리뷰 상세 조회")
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ReviewInfoDto> getReviewDetail(
            @Parameter(description = "리뷰 고유 Id") @PathVariable("reviewId") Long reviewId
    ){
        ReviewInfoDto reviewDetail = reviewService.getReviewDetail(reviewId);
        return ResponseEntity.ok(reviewDetail);
    }



}
