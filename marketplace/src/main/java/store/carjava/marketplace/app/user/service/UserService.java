package store.carjava.marketplace.app.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.app.reservation.entity.Reservation;
import store.carjava.marketplace.app.reservation.repository.ReservationRepository;
import store.carjava.marketplace.app.user.dto.UserResponse;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.user.repository.UserRepository;
import store.carjava.marketplace.common.security.UserNotAuthenticatedException;
import store.carjava.marketplace.common.util.user.UserResolver;

import java.util.Optional;

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




    }
}
