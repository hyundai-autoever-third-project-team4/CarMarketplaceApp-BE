package store.carjava.marketplace.app.user.dto;

import java.util.List;

public record ReservationListResponse(List<UserReservationDto> reservationList) {
    public static ReservationListResponse of(List<UserReservationDto> reservationList) {
        return new ReservationListResponse(reservationList);
    }
}
