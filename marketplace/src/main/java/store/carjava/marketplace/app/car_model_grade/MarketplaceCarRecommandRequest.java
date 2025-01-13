package store.carjava.marketplace.app.car_model_grade;

import io.swagger.v3.oas.annotations.media.Schema;

public record MarketplaceCarRecommandRequest(
        @Schema(description = "차 종류", example = "승용")
        String vehicleType,

        @Schema(description = "예산", example = "3000")
        Long budget
) {

}
