package store.carjava.marketplace.app.marketplace_car_image.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record MarketplaceCarImageInfoDto(

        @Schema(description = "차량 이미지 ID", example = "1001")
        Long id,

        @Schema(description = "마켓플레이스 차량 ID", example = "GGI241223011164")
        String marketplaceCarId,

        @Schema(description = "이미지 URL", example = "https://example.com/images/car-image.jpg")
        String imageUrl
) {
}