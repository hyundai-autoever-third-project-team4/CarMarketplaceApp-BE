package store.carjava.marketplace.app.review.controller;


import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import store.carjava.marketplace.app.review.dto.*;
import store.carjava.marketplace.app.review.service.ReviewService;
import store.carjava.marketplace.common.util.image.ImageUploader;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "리뷰 API", description = "차량 리뷰 관련 API를 제공합니다.")
public class ReviewController {
    private final ReviewService reviewService;
    private final ImageUploader imageUploader;

    @Operation(summary = "구매한 차량 리뷰 작성", description = "구매한 차량 리뷰작성")
    @PostMapping("/review")
    public ResponseEntity<ReviewCreateResponse> createReview(
            @RequestParam("carId") String carId,
            @RequestParam("content") String content,
            @RequestParam("starRate") Double starRate,
            @RequestParam(value = "files", required = false) List<MultipartFile> files

    ) throws IOException, java.io.IOException {
        // 이미지 파일이 전송된 경우에만 S3에 업로드 수행
        List<String> imageUrls = files != null && !files.isEmpty()
                ? imageUploader.uploadMultiFiles(files, "reviews")
                : List.of();


        // ReviewCreateRequest 객체 생성
        ReviewCreateRequest request = new ReviewCreateRequest(carId, content, starRate);

        // 리뷰 생성 서비스 호출
        ReviewCreateResponse response = reviewService.createReview(request, imageUrls);
        return ResponseEntity.ok(response);

    }

    @Operation(summary = "내가 쓴 리뷰 조회", description = "마이페이지에서 나의 리뷰 조회 api")
    @GetMapping("/mypage/reviews")
    public ResponseEntity<ReviewInfoListDto> getUserReviews(
    ) {
        ReviewInfoListDto userReviews = reviewService.getMyReviews();
        return ResponseEntity.ok(userReviews);
    }

    @Operation(summary = "내가 쓴 리뷰 삭제", description = "마이페이지 리뷰 삭제")
    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<ReviewDeleteResponse> deleteReview(
            @Parameter(description = "삭제할 리뷰 ID") @PathVariable("reviewId") Long reviewId
    ) {
        ReviewDeleteResponse responseDto = reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(responseDto);
    }

    //리뷰controller 말고 차량조회에서 CarDetailResponseDto에 ReviewInfoList도 함께 조회하도록 수정 필요
    @Operation(summary = "차량 동일 모델 리뷰 조회", description = "차량 상세에서 동일모델의 리뷰 조회")
    @GetMapping("/cars/{carId}/review")
    public ResponseEntity<ReviewInfoListDto> getCarReviews(
            @Parameter(description = "carId") @PathVariable("carId") String carId
    ){
        ReviewInfoListDto carReviews = reviewService.getCarReviews(carId);
        return ResponseEntity.ok(carReviews);
    }

    @Operation(summary = "리뷰 상세 조회", description = "리뷰 상세 조회")
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ReviewInfoDto> getReviewDetail(
            @Parameter(description = "리뷰 고유 Id") @PathVariable("reviewId") Long reviewId
    ){
        ReviewInfoDto reviewDetail = reviewService.getReviewDetail(reviewId);
        return ResponseEntity.ok(reviewDetail);
    }



}
