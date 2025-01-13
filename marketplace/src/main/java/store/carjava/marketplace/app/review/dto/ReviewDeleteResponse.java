package store.carjava.marketplace.app.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReviewDeleteResponse(
        @Schema(description = "리뷰 id",example = "1")
        Long reviewId,
        @Schema(description = "삭제 완료 메시지", example = "리뷰가 성공적으로 삭제됨.")
        String message) {
    public static ReviewDeleteResponse of(
            Long reviewId) {
        return new ReviewDeleteResponse(reviewId, "리뷰가 성공적으로 삭제되었습니다.");
    }
}
