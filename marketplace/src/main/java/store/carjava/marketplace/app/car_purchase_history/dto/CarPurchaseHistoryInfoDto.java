package store.carjava.marketplace.app.car_purchase_history.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CarPurchaseHistoryInfoDto(

        @Schema(description = "구매 기록 ID", example = "1")
        Long id,

        @Schema(description = "구매 차량 ID", example = "GGI241223011164")
        String marketplaceCarId,

        @Schema(description = "구매 유저 ID", example = "1")
        Long userId
) {
}