package store.carjava.marketplace.app.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;

@Schema(description = "예약 생성 요청 DTO")
public record ReservationCreateRequest(

        @Schema(description = "마켓플레이스 차량 ID", example = "GGI241223011164")
        String marketplaceCarId,

        @Schema(description = "예약 날짜 (YYYY-MM-DD 형식)", example = "2025-01-15")
        LocalDate reservationDate,

        @Schema(description = "예약 시간 (HH:mm:ss 형식)", example = "12:30:00")
        LocalTime reservationTime
) {
}
