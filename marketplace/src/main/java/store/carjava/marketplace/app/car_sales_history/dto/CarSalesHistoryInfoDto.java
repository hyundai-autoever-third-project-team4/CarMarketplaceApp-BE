package store.carjava.marketplace.app.car_sales_history.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CarSalesHistoryInfoDto(

        @Schema(description = "판매 기록 ID", example = "12345")
        Long id,

        @Schema(description = "판매 차량 ID", example = "marketplace-001")
        String marketplaceCarId,

        @Schema(description = "판매 유저 ID", example = "67890")
        Long userId
) {
}