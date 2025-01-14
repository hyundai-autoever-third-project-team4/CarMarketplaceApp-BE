package store.carjava.marketplace.app.reservation.dto;

import store.carjava.marketplace.app.reservation.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationCreateResponse(
        Long reservationId,
        LocalDate reservationDate,
        LocalTime reservationTime,
        LocalDateTime createdAt
) {
    public static ReservationCreateResponse from(Reservation reservation) {
        return new ReservationCreateResponse(
                reservation.getId(),
                reservation.getReservationDate(),
                reservation.getReservationTime(),
                reservation.getCreatedAt()
        );
    }
}
