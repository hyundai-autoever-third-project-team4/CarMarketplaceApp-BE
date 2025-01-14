package store.carjava.marketplace.app.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.carjava.marketplace.app.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationCustomRepository {

}
