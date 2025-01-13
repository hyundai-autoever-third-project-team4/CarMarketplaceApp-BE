package store.carjava.marketplace.app.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import store.carjava.marketplace.app.review.entity.Review;

import java.time.LocalDateTime;

public record ReviewCreateResponse(
        @Schema(description = "생성된 리뷰 ID", example = "1")
        Long reviewId,

        @Schema(title = "별점", example = "4.5")
        Double starRate,

        @Schema(description = "리뷰 내용", example = "차량 상태가 매우 좋았습니다.")
        String content,

        @Schema(description = "리뷰 작성 시간")
        LocalDateTime createdAt,

        @Schema(description = "생성 완료 메시지", example = "리뷰가 성공적으로 생성되었습니다.")
        String message
) {
    public static ReviewCreateResponse of(Review review) {
        return new ReviewCreateResponse(
                review.getId(),
                review.getStarRate(),
                review.getContent(),
                review.getCreatedAt(),
                "리뷰 생성 성공"
        );
    }

}



