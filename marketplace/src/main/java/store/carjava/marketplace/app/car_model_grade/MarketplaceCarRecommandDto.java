package store.carjava.marketplace.app.car_model_grade;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record MarketplaceCarRecommandDto(
        @Schema(description = "차 이름", example = "2022 그랜저(IG) 하이브리드 르블랑")
        String name,

        @Schema(description = "가격", example = "3390")
        Long price,

        @Schema(description = "등록일자", example = "2021-07-15")
        LocalDate registrationDate,

        @Schema(description = "주행거리", example = "16510")
        Long mileage,

        @Schema(description = "차 이미지 주소", example = "https://certified...")
        String mainImage
) {

}
