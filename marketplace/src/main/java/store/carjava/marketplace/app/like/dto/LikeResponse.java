package store.carjava.marketplace.app.like.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import store.carjava.marketplace.app.like.entity.Like;

public record LikeResponse(
        @Schema(description = "carId", example = "HDN241115010468")
        String carId,
        @Schema(description = "유저Id", example = "1")
        Long userId,
        @Schema(description = "찜 가능 여부", example = "true")
        boolean liked,
        @Schema(description = "해당 차량에 대한 전체 찜 수", example = "120")
        Long totalLikeCount

) {

    public static LikeResponse of(Like like, boolean liked, Long totalLikeCount){
        return new LikeResponse(
                like.getMarketplaceCar().getId(),
                like.getId(),
                liked,
                totalLikeCount
        );
    }
}

