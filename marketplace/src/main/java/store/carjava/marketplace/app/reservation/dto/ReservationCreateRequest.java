package store.carjava.marketplace.app.reservation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservationCreateRequest(
        String marketplaceCarId,
        LocalDate reservationDate,
        LocalDateTime reservationTime
) {
}
