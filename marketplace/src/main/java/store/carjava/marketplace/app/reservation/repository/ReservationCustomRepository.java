package store.carjava.marketplace.app.reservation.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ReservationCustomRepository {
    // 예약 가능한지를 찾는 메소드
    boolean isReservationAvailable(String marketplaceCarId, LocalDate reservationDate);
}
