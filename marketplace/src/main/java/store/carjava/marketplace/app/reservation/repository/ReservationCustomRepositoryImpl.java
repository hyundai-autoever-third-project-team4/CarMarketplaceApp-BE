package store.carjava.marketplace.app.reservation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.reservation.entity.QReservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<LocalDate, Boolean> getReservationAvailability(String marketplaceCarId, int limitDays) {
        QReservation reservation = QReservation.reservation;

        // 오늘 날짜부터 limitDays까지의 날짜 범위 계산
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(limitDays);

        // 특정 차량의 예약된 날짜 목록 조회
        List<LocalDate> reservedDates = queryFactory
                .select(reservation.reservationDate)
                .from(reservation)
                .where(
                        reservation.marketplaceCar.id.eq(marketplaceCarId),
                        reservation.reservationDate.between(today.plusDays(1), endDate)
                )
                .fetch();

        // 날짜별 예약 여부 Map 생성
        Map<LocalDate, Boolean> availabilityMap = new LinkedHashMap<>();
        for (LocalDate date = today.plusDays(1); !date.isAfter(endDate); date = date.plusDays(1)) {
            availabilityMap.put(date, !reservedDates.contains(date)); // 예약이 없으면 true
        }

        return availabilityMap;
    }
}
