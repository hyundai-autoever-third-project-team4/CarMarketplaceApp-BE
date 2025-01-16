package store.carjava.marketplace.app.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import store.carjava.marketplace.app.reservation.entity.Reservation;
import store.carjava.marketplace.app.user.entity.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "마이페이지 첫화면/이름 및 가까운 예약 내역")
public record UserResponse(
        @Schema(description = "유저이름", example = "홍길동")
        String userName,

        @Schema(description = "가장가까운 예약 내역")
        List<UserReservationDto> reservation

) {
        public static UserResponse of(User user, Reservation reservation){
                if (reservation == null) {
                        return new UserResponse(
                                user.getName(),
                                new ArrayList<>()
                        );
                }

                List<UserReservationDto> reservationDto = new ArrayList<>();
                reservationDto.add(UserReservationDto.of(reservation));
                return new UserResponse(
                        user.getName(),
                       reservationDto
                );

        }

        public static UserResponse empty(User user) {
                return new UserResponse(
                        user.getName(),
                        new ArrayList<>()
                );
        }

}
