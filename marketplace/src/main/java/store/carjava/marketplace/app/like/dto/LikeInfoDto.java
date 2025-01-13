package store.carjava.marketplace.app.like.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record LikeInfoDto(

        @Schema(description = "좋아요 기록 ID", example = "12345")
        Long id,

        @Schema(description = "좋아요를 누른 차량 ID", example = "marketplace-001")
        String marketplaceCarId,

        @Schema(description = "좋아요를 누른 유저 ID", example = "67890")
        Long userId
) {
}