package store.carjava.marketplace.app.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import store.carjava.marketplace.app.reservation.entity.Reservation;
import store.carjava.marketplace.app.user.entity.User;

import java.time.LocalDate;
import java.time.LocalTime;

@Schema(description = "마이페이지 첫화면/이름 및 가까운 예약 내역")
public record UserResponse(
        @Schema(description = "유저이름", example = "홍길동")
        String userName,

        @Schema(description = "예약한 차이름", example = "2023GV70 가솔린 2.5터보 AWD 스탠다드디자인")
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
        public static UserResponse of(User user, Reservation reservation){
                if (reservation == null) {
                        return new UserResponse(
                                user.getName(),
                                null,
                                null,
                                null,
                                null,
                                null
                        );
                }

                return new UserResponse(
                        user.getName(),
                        reservation.getMarketplaceCar().getCarDetails().getName(),
                        reservation.getMarketplaceCar().getMainImage(),
                        reservation.getMarketplaceCar().getTestDriveCenter().getName(),
                        reservation.getReservationDate(),
                        reservation.getReservationTime()
                );

        }

        public static UserResponse empty(User user) {
                return new UserResponse(
                        user.getName(),
                        null,
                        null,
                        null,
                        null,
                        null
                );
        }

}
