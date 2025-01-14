package store.carjava.marketplace.app.reservation.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.exception.MarketplaceCarIdNotFoundException;
import store.carjava.marketplace.app.marketplace_car.repository.MarketplaceCarRepository;
import store.carjava.marketplace.app.reservation.dto.ReservationCreateRequest;
import store.carjava.marketplace.app.reservation.dto.ReservationCreateResponse;
import store.carjava.marketplace.app.reservation.entity.Reservation;
import store.carjava.marketplace.app.reservation.exception.ReservationAlreadyExistsException;
import store.carjava.marketplace.app.reservation.repository.ReservationRepository;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.common.util.UserResolver;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

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
}
