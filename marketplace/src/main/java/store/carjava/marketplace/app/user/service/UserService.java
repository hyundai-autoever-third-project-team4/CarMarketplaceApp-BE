package store.carjava.marketplace.app.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.app.car_sales_history.entity.CarSalesHistory;
import store.carjava.marketplace.app.car_sales_history.repository.CarSalseHistoryRepository;
import store.carjava.marketplace.app.reservation.entity.Reservation;
import store.carjava.marketplace.app.reservation.repository.ReservationRepository;
import store.carjava.marketplace.app.user.dto.*;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.user.repository.UserRepository;
import store.carjava.marketplace.common.security.UserNotAuthenticatedException;
import store.carjava.marketplace.common.util.user.UserResolver;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final UserResolver userResolver;
    private final CarSalseHistoryRepository carSalseHistoryRepository;

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
    public UserReservationListResponse getUserReservationList() {
        // 1. 로그인한 유저 정보 가져오기.
        User currentUser = userResolver.getCurrentUser();
        if (currentUser == null) {
            throw new UserNotAuthenticatedException();
        }

        List<UserReservationDto> ReservationList = reservationRepository.findByUserOrderByReservationDateDescReservationTimeDesc(currentUser)
                .stream()
                .map(UserReservationDto::of)
                .collect(Collectors.toList());

        return UserReservationListResponse.of(ReservationList);
    }

    //유저가 판매한 차량 리스트 조회
    public UserSellCarListResponse getUserSellList() {

        // 1. 로그인한 유저 정보 가져오기.
        User currentUser = userResolver.getCurrentUser();
        if (currentUser == null) {
            throw new UserNotAuthenticatedException();
        }

        // 2. car_sales_history에서 판매 내역 조회
        List<CarSalesHistory> sellList =carSalseHistoryRepository.findByUserOrderByIdDesc(currentUser);

        // 3. CarSalesHistory -> UserSellCarDto 변환
        List<UserSellCarDto> userSellCarDtos = sellList.stream()
                .map(history -> new UserSellCarDto(
                        history.getMarketplaceCar().getCarDetails().getName(),
                        history.getMarketplaceCar().getCarDetails().getLicensePlate(),
                        history.getMarketplaceCar().getCarDetails().getRegistrationDate(),
                        history.getMarketplaceCar().getCarDetails().getMileage(),
                        history.getMarketplaceCar().getPrice(),
                        history.getMarketplaceCar().getMainImage(),
                        history.getMarketplaceCar().getStatus().toString()
                ))
                .collect(Collectors.toList());

        // 4. UserSellCarListResponse 생성 및 반환
        return UserSellCarListResponse.of(userSellCarDtos);
    }
}
