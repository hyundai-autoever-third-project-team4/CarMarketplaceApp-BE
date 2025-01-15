package store.carjava.marketplace.app.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.app.reservation.entity.Reservation;
import store.carjava.marketplace.app.reservation.repository.ReservationRepository;
import store.carjava.marketplace.app.user.dto.ReservationListResponse;
import store.carjava.marketplace.app.user.dto.UserReservationDto;
import store.carjava.marketplace.app.user.dto.UserResponse;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.user.repository.UserRepository;
import store.carjava.marketplace.common.security.UserNotAuthenticatedException;
import store.carjava.marketplace.common.util.user.UserResolver;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final UserResolver userResolver;

    public UserResponse getUserPage() {

        // 1. 로그인한 유저 정보 가져오기.
        User currentUser = userResolver.getCurrentUser();
        if (currentUser == null) {
            throw new UserNotAuthenticatedException();
        }

        // 2. 가장 가까운 예약 내역 조회
        Optional<Reservation> upcomingReservation = reservationRepository.findUpcomingReservation(currentUser);


        // 3. UserResponse 생성
        // - 예약이 있는 경우: 유저 정보와 예약 정보를 함께 반환
        // - 예약이 없는 경우: 유저 정보만 반환
        return upcomingReservation
                .map(reservation -> UserResponse.of(currentUser, reservation))
                .orElseGet(() -> UserResponse.empty(currentUser));



    }

    //유저 시승 예약 내역 리스트 조회
    public ReservationListResponse getUserReservationList() {
        // 1. 로그인한 유저 정보 가져오기.
        User currentUser = userResolver.getCurrentUser();
        if (currentUser == null) {
            throw new UserNotAuthenticatedException();
        }

        List<UserReservationDto> ReservationList = reservationRepository.findByUserOrderByReservationDateDescReservationTimeDesc(currentUser)
                .stream()
                .map(UserReservationDto::of)
                .collect(Collectors.toList());

        return ReservationListResponse.of(ReservationList);
    }
}
