package store.carjava.marketplace.app.marketplace_car.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record MarketplaceCarRecommandRequest(
        @Schema(description = "차 id, 첫 추천인 경우에는 null", example = "GJU241125010629")
        String carId,

        @Schema(description = "차 종류", example = "['승용', 'SUV']")
        List<String> vehicleType,

        @Schema(description = "예산", example = "3000")
        Long budget
) {

}
