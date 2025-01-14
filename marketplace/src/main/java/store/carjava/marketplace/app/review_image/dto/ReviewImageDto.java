package store.carjava.marketplace.app.review_image.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import store.carjava.marketplace.app.review_image.entity.ReviewImage;

public record ReviewImageDto(
        @Schema(description = "리뷰 이미지 id", example = "1")
        Long imageId,
        @Schema(description = "이미지 url", example = "https://your-s3-bucket.s3.amazonaws.com/images/example.jpg")
        String imageUrl,

        @Schema(description = "리뷰 ID", example = "1")
        Long reviewId

) {
    public static ReviewImageDto of(ReviewImage image) {
        return new ReviewImageDto(
                image.getId(),
                image.getImageUrl(),
                image.getReview().getId()
        );
    }
}
