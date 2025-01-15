package store.carjava.marketplace.app.user.dto;

import java.util.List;

public record UserReservationListResponse(List<UserReservationDto> reservationList) {
    public static UserReservationListResponse of(List<UserReservationDto> reservationList) {
        return new UserReservationListResponse(reservationList);
    }
}
