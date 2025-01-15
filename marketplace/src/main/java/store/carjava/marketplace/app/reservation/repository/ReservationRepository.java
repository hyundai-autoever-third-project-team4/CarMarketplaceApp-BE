package store.carjava.marketplace.app.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.carjava.marketplace.app.reservation.entity.Reservation;
import store.carjava.marketplace.app.user.entity.User;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationCustomRepository {
    //마이페이지 유저의 전체 예약내역 조회
    List<Reservation> findByUserOrderByReservationDateDescReservationTimeDesc(User user);
}
