package store.carjava.marketplace.app.reservation.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.reservation.entity.QReservation;
import store.carjava.marketplace.app.reservation.entity.Reservation;
import store.carjava.marketplace.app.user.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

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


    @Override
    public Optional<Reservation> findUpcomingReservation(User user) {
        QReservation reservation = QReservation.reservation;

        // 현재 날짜와 시간을 가져옵니다
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        // QueryDSL을 사용하여 다음 예약을 찾습니다
        return Optional.ofNullable(queryFactory
                .selectFrom(reservation)
                .where(
                        reservation.user.eq(user)
                                .and(
                                        // 미래 날짜의 예약이거나
                                        reservation.reservationDate.gt(currentDate)
                                                .or(
                                                        // 오늘 날짜이면서 현재 시간 이후의 예약
                                                        reservation.reservationDate.eq(currentDate)
                                                                .and(reservation.reservationTime.gt(currentTime))
                                                )
                                )
                )
                // 날짜와 시간 순으로 정렬
                .orderBy(
                        reservation.reservationDate.asc(),
                        reservation.reservationTime.asc()
                )
                // 가장 가까운 예약 하나만 가져옵니다
                .limit(1)
                .fetchOne());
    }
    @Override
    public Page<Reservation> findAllWithFilters(String reservationName, String licensePlate, String status, String reservationDate, Pageable pageable) {
        QReservation reservation = QReservation.reservation;

        BooleanExpression predicate = reservation.isNotNull();

        if (reservationName != null && !reservationName.trim().isEmpty()) {
            predicate = predicate.and(reservation.user.name.containsIgnoreCase(reservationName));
        }

        if (licensePlate != null && !licensePlate.trim().isEmpty()) {
            predicate = predicate.and(reservation.marketplaceCar.carDetails.licensePlate.containsIgnoreCase(licensePlate));
        }

        if (status != null && !status.trim().isEmpty()) {
//            predicate = predicate.and(reservation.status.eq(status));
        }

        if (reservationDate != null && !reservationDate.trim().isEmpty()) {
            LocalDate date = LocalDate.parse(reservationDate);
            predicate = predicate.and(reservation.reservationDate.eq(date));
        }

        long total = queryFactory.selectFrom(reservation)
                .where(predicate)
                .fetchCount();

        var content = queryFactory.selectFrom(reservation)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new org.springframework.data.domain.PageImpl<>(content, pageable, total);
    }

}
