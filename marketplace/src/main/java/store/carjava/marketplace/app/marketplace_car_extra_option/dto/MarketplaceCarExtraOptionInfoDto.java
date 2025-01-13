package store.carjava.marketplace.app.marketplace_car_extra_option.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
@Builder
public record MarketplaceCarExtraOptionInfoDto(

        @Schema(description = "추가 옵션 ID", example = "1001")
        Long id,

        @Schema(description = "마켓플레이스 차량 ID", example = "GGI241223011164")
        String marketplaceCarId,

        @Schema(description = "추가 옵션 이름", example = "썬루프")
        String name,

        @Schema(description = "추가 옵션 가격 (원 단위)", example = "2000000")
        int price
) {
}