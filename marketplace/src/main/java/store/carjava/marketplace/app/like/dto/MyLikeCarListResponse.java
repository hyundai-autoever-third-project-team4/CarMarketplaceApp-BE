package store.carjava.marketplace.app.like.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record MyLikeCarListResponse(
        @Schema(description = "찜한 차량 목록")
        List<MyLikeCarDto> cars,

        @Schema(description = "전체 좋아요한 차량 수", example = "42")
        long totalCount
) {
    public static MyLikeCarListResponse of(List<MyLikeCarDto> cars, long totalCount) {
        return new MyLikeCarListResponse(cars, totalCount);
    }
}
