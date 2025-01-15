package store.carjava.marketplace.app.reservation.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public interface ReservationCustomRepository {
    // 예약 가능한지를 찾는 메소드
    boolean isReservationAvailable(String marketplaceCarId, LocalDate reservationDate);

    // 특정 날짜 범위의 예약 상태 조회
    Map<LocalDate, Boolean> getReservationAvailability(String marketplaceCarId, int limitDays);
}
