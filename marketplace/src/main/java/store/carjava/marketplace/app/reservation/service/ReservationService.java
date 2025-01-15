package store.carjava.marketplace.app.reservation.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.exception.MarketplaceCarIdNotFoundException;
import store.carjava.marketplace.app.marketplace_car.repository.MarketplaceCarRepository;
import store.carjava.marketplace.app.reservation.dto.ReservationCreateRequest;
import store.carjava.marketplace.app.reservation.dto.ReservationCreateResponse;
import store.carjava.marketplace.app.reservation.dto.ReservationListResponse;
import store.carjava.marketplace.app.reservation.entity.Reservation;
import store.carjava.marketplace.app.reservation.exception.ReservationAlreadyExistsException;
import store.carjava.marketplace.app.reservation.repository.ReservationRepository;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.common.util.user.UserResolver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private static final int LIMIT_DAYS = 14;   // 조회할 수 있는 일수

    private final ReservationRepository reservationRepository;
    private final UserResolver userResolver;
    private final MarketplaceCarRepository marketplaceCarRepository;

    public ReservationCreateResponse createReservation(ReservationCreateRequest request) {
        // 1) 현재 로그인 한 유저 조회
        User currentUser = userResolver.getCurrentUser();

        // 2) 자동차 정보 조회
        MarketplaceCar marketplaceCar = marketplaceCarRepository.findById(request.marketplaceCarId())
                .orElseThrow(MarketplaceCarIdNotFoundException::new);

        // 3) 예약 정보 조회
        if (!reservationRepository.isReservationAvailable(
                request.marketplaceCarId(),
                request.reservationDate()
        )) {
            throw new ReservationAlreadyExistsException();
        }

        // 4) 예약 엔티티 생성
        Reservation reservation = Reservation.builder()
                .marketplaceCar(marketplaceCar)
                .user(currentUser)
                .reservationDate(request.reservationDate())
                .reservationTime(request.reservationTime())
                .createdAt(LocalDateTime.now())
                .build();

        // 5) 예약 엔티티 db에 저장
        Reservation savedReservation = reservationRepository.save(reservation);

        // 6) 예약 정보 response dto 생성 후 리턴
        return ReservationCreateResponse.from(savedReservation);
    }

    public ReservationListResponse getMarketplaceCarReservations(String marketplaceCarId) {
        // 1) marketplaceCarId에 해당하는 entity 조회
        MarketplaceCar marketplaceCar = marketplaceCarRepository.findById(marketplaceCarId)
                .orElseThrow(MarketplaceCarIdNotFoundException::new);

        // 2) Reservation Table에서 해당 marketplace car에 대한
        Map<LocalDate, Boolean> reservationAvailability = reservationRepository.getReservationAvailability(marketplaceCarId, LIMIT_DAYS);

        return ReservationListResponse.of(marketplaceCarId, reservationAvailability);
    }
}
