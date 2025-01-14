package store.carjava.marketplace.app.reservation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.carjava.marketplace.app.reservation.entity.QReservation;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class ReservationCustomRepositoryImpl implements ReservationCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean isReservationAvailable(String marketplaceCarId, LocalDate reservationDate) {
        QReservation reservation = QReservation.reservation;

        // 쿼리 작성
        Long count = queryFactory
                .select(reservation.count())
                .from(reservation)
                .where(
                        reservation.marketplaceCar.id.eq(marketplaceCarId),
                        reservation.reservationDate.eq(reservationDate)
                )
                .fetchOne();

        return count == 0; // 예약이 없다면 true 반환
    }
}
