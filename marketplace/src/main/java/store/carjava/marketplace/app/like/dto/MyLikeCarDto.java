package store.carjava.marketplace.app.like.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;

import java.time.LocalDate;

public record MyLikeCarDto(
        @Schema(description = "차량 ID", example = "GGI241223011164")
        String carId,

        @Schema(description = "차량이름", example = "2021G80 가솔린 2.5 터보 AWD 시그니처 디자인 셀렉션Ⅰ")
        String name,

        @Schema(description = "번호판", example = "303누7499")
        String licensePlate,

        @Schema(description = "차량 대표이미지URL", example = "https://certified-static.hyundai.com/contents/go.....")
        String mainImageUrl,

        @Schema(description = "가격", example = "24700000")
        Long price,

        @Schema(description = "주행거리", example = "20500")
        int mileage,

        @Schema(description = "등록일", example = "2020-09-10")
        LocalDate registrationDate



) {

    public static MyLikeCarDto of(MarketplaceCar car) {
        return new MyLikeCarDto(
                car.getId(),
                car.getCarDetails().getName(),
                car.getCarDetails().getLicensePlate(),
                car.getMainImage(),
                car.getPrice(),
                car.getCarDetails().getMileage(),
                car.getCarDetails().getRegistrationDate()

        );
    }
}
