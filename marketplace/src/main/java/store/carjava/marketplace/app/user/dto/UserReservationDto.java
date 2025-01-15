package store.carjava.marketplace.app.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import store.carjava.marketplace.app.reservation.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record UserReservationDto(

        @Schema(description = "예약한 차이름", example = "2023 GV70 가솔린 2.5터보 AWD 스탠다드디자인")
        String carName,

        @Schema(description = "차량사진", example = "https://certified-static.hyundai.com/contents/goods/shootConts/tobepic/02/exterior/GGK241216011103/PRD602_233.JPG/dims/crop/2304x1536+600+770/resize/380x253/optimize")
        String mainImage,

        @Schema(description = "시승소 이름", example = "부산시승소")
        String driveCenter,

        @Schema(description = "예약 날짜", example = "2023-12-01")
        LocalDate reservationDate,

        @Schema(description = "예약 시간", example = "12:30:00")
        LocalTime reservationTime
) {
        public static UserReservationDto of(Reservation reservation) {
                return new UserReservationDto(
                        reservation.getMarketplaceCar().getCarDetails().getName(),
                        reservation.getMarketplaceCar().getMainImage(),
                        reservation.getMarketplaceCar().getTestDriveCenter().getName(),
                        reservation.getReservationDate(),
                        reservation.getReservationTime()
                );

        }
}
