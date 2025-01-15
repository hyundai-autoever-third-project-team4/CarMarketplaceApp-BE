package store.carjava.marketplace.app.reservation.dto;

import java.time.LocalDate;
import java.util.Map;

public record ReservationListResponse(
        String marketplaceCarId,
        Map<LocalDate, Boolean> marketplaceCarAvailability
) {
    public static ReservationListResponse of(String marketplaceCarId, Map<LocalDate, Boolean> marketplaceCarAvailability) {
        return new ReservationListResponse(
                marketplaceCarId,
                marketplaceCarAvailability
        );
    }
}
