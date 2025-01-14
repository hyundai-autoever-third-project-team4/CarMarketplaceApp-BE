package store.carjava.marketplace.app.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequest(
        String marketplaceCarId,
        LocalDate reservationDate,
        LocalTime reservationTime
) {
}
