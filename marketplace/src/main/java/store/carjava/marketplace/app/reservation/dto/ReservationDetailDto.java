package store.carjava.marketplace.app.reservation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import store.carjava.marketplace.app.reservation.entity.Reservation;

public record ReservationDetailDto(
        Long id,
        String reservationNumber,
        String carBrand,
        String carModel,
        String carLicensePlate,
        String customerName,
        String customerPhone,
        LocalDateTime createdAt,
        LocalDate reservationDate,
        LocalTime reservationTime,
        String status,
        String carImageUrl,
        String testDriverCenterName
) {

    // 정적 팩토리 메서드
    public static ReservationDetailDto fromEntity(Reservation reservation) {
        String reservationNumber = "TD" +
                reservation.getCreatedAt().toLocalDate().toString().replace("-", "") +
                "-" + String.format("%03d", reservation.getId());

        String status = determineStatus(reservation);

        return new ReservationDetailDto(
                reservation.getId(),
                reservationNumber,
                reservation.getMarketplaceCar().getCarDetails().getBrand(),
                reservation.getMarketplaceCar().getCarDetails().getModel(),
                reservation.getMarketplaceCar().getCarDetails().getLicensePlate(),
                reservation.getUser().getName(),
                reservation.getUser().getPhone(),
                reservation.getCreatedAt(),
                reservation.getReservationDate(),
                reservation.getReservationTime(),
                status,
                reservation.getMarketplaceCar().getMainImage(),
                reservation.getMarketplaceCar().getTestDriveCenter().getName()
        );
    }

    // 상태 결정 메서드
    private static String determineStatus(
            store.carjava.marketplace.app.reservation.entity.Reservation reservation) {
        LocalDateTime reservationDateTime = LocalDateTime.of(reservation.getReservationDate(),
                reservation.getReservationTime());
        LocalDateTime now = LocalDateTime.now();

        if (reservationDateTime.isBefore(now)) {
            return "시승 완료";
        } else {
            return "예약 확정";
        }
    }
}
