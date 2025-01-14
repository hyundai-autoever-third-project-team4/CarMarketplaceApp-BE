package store.carjava.marketplace.app.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;

@Schema(description = "마이페이지-이름 및 가까운 예약 내역")
public record UserResponse(
        @Schema(description = "유저이름", example = "홍길동")
        String userName,

        String carName,

        String driveCenter,

        @Schema(description = "예약 날짜", example = "2023-12-01")
        LocalDate reservationDate,

        @Schema(description = "예약 시간", example = "2023-12-01T14:00:00")
        LocalTime reservationTime


) {

}
