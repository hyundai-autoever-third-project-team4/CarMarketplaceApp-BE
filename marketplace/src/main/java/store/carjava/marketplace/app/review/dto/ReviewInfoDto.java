package store.carjava.marketplace.app.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import store.carjava.marketplace.app.review.entity.Review;

import java.time.LocalDateTime;

public record ReviewInfoDto(
        @Schema(description = "생성된 리뷰 ID", example = "1")
        Long reviewId,

        @Schema(description = "별점", example = "4.0")
        Double starRate,

        @Schema(description = "리뷰 내용", example = "차량 상태가 매우 좋았습니다.")
        String content,

        @Schema(description = "리뷰 작성 일시")
        LocalDateTime createdAt,

        @Schema(description = "차이름", example = "2023 GV70 가솔린 2.5 터보 AWD 스탠다드 디자인")
        String carName,

        @Schema(description = "차 고유 ID", example = "GJK241018009707")
        String carId,

        @Schema(description = "차종", example = "그랜저")
        String carModel,

        @Schema(description = "리뷰 작성자 이메일", example = "abc@naver.com")
        String writerEmail



) {
    public static ReviewInfoDto of(Review review) {
        return new ReviewInfoDto(
                review.getId(),
                review.getStarRate(),
                review.getContent(),
                review.getCreatedAt(),
                review.getMarketplaceCar().getCarDetails().getName(),
                review.getMarketplaceCar().getId(),
                review.getMarketplaceCar().getCarDetails().getModel(),
                review.getUser().getEmail()

                );
    }
}
