package store.carjava.marketplace.app.marketplace_car.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;

import java.text.DecimalFormat;

public record MarketplaceCarRecommandInfoDto(
        @Schema(description = "차 id", example = "GJU241125010629")
        String id,

        @Schema(description = "차 이름", example = "2022 그랜저(IG) 하이브리드 르블랑")
        String name,

        @Schema(description = "가격(천만원)", example = "3390")
        Long price,

        @Schema(description = "등록일자", example = "21년 07월")
        String registrationDate,

        @Schema(description = "주행거리", example = "16510km")
        String mileage,

        @Schema(description = "차 이미지 주소", example = "https://certified...")
        String mainImage
) {
        public static MarketplaceCarRecommandInfoDto of(MarketplaceCar marketplaceCar){
                if(marketplaceCar == null) return null;

                DecimalFormat formatter = new DecimalFormat("#,###");
                String mileage = formatter.format(marketplaceCar.getCarDetails().getMileage()) + "km";

                formatter = new DecimalFormat("00");
                Integer year = marketplaceCar.getCarDetails().getRegistrationDate().getYear();
                Integer month = marketplaceCar.getCarDetails().getRegistrationDate().getMonthValue();
                String registrationDate = year + "년 " + formatter.format(month) + "일";

                return new MarketplaceCarRecommandInfoDto(
                        marketplaceCar.getId(),
                        marketplaceCar.getCarDetails().getName(),
                        marketplaceCar.getPrice() / 10000,
                        registrationDate,
                        mileage,
                        marketplaceCar.getMainImage()
                );
        }
}
