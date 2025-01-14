package store.carjava.marketplace.app.reservation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record ReservationInfoDto(

        @Schema(description = "예약 ID", example = "1001")
        Long id,

        @Schema(description = "마켓플레이스 차량 ID", example = "GGI241223011164")
        String marketplaceCarId,

        @Schema(description = "예약자 유저 ID", example = "67890")
        Long userId,

        @Schema(description = "예약 날짜", example = "2023-12-01")
        LocalDate reservationDate,

        @Schema(description = "예약 시간", example = "2023-12-01T14:00:00")
        LocalTime reservationTime,

        @Schema(description = "예약 생성 날짜 및 시간", example = "2023-11-20T10:30:00")
        LocalDateTime createdAt
) {
}