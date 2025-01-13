package store.carjava.marketplace.app.car_model_grade;

import io.swagger.v3.oas.annotations.media.Schema;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;

import java.text.DecimalFormat;

public record MarketplaceCarRecommandInfoDto(
        @Schema(description = "차 이름", example = "2022 그랜저(IG) 하이브리드 르블랑")
        String name,

        @Schema(description = "가격", example = "3390만원")
        String price,

        @Schema(description = "등록일자", example = "21년 07월")
        String registrationDate,

        @Schema(description = "주행거리", example = "16510km")
        String mileage,

        @Schema(description = "차 이미지 주소", example = "https://certified...")
        String mainImage
) {
        public static MarketplaceCarRecommandInfoDto of(MarketplaceCar marketplaceCar){
                DecimalFormat formatter = new DecimalFormat("#,###");
                String price = formatter.format(marketplaceCar.getPrice() / 10000) + "만원";
                String mileage = formatter.format(marketplaceCar.getCarDetails().getMileage()) + "km";

                formatter = new DecimalFormat("00");
                Integer month = marketplaceCar.getCarDetails().getRegistrationDate().getMonthValue();
                Integer day = marketplaceCar.getCarDetails().getRegistrationDate().getDayOfMonth();
                String registrationDate = month + "년 " + formatter.format(day) + "일";

                return new MarketplaceCarRecommandInfoDto(
                        marketplaceCar.getCarDetails().getName(),
                        price,
                        registrationDate,
                        mileage,
                        marketplaceCar.getMainImage()
                );
        }
}
