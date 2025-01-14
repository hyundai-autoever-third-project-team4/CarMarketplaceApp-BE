package store.carjava.marketplace.app.reservation.repository;

import store.carjava.marketplace.app.reservation.entity.Reservation;
import store.carjava.marketplace.app.user.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public interface ReservationCustomRepository {
    // 예약 가능한지를 찾는 메소드
    boolean isReservationAvailable(String marketplaceCarId, LocalDate reservationDate);

    // 특정 날짜 범위의 예약 상태 조회
    Map<LocalDate, Boolean> getReservationAvailability(String marketplaceCarId, int limitDays);


    //마이페이지 가까운 예약내역 조회
    Optional<Reservation> findUpcomingReservation(User user);
}
