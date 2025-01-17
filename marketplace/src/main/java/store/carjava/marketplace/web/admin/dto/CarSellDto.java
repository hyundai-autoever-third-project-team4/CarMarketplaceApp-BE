package store.carjava.marketplace.web.admin.dto;

import store.carjava.marketplace.app.car_purchase_history.entity.CarPurchaseHistory;

import java.time.LocalDateTime;

public record CarSellDto(
        String carName,
        Long price,
        String userName,
        String licensePlate,
        String mainImage,
        LocalDateTime paymentDate,
        String status,
        Long orderId

) {
    public static CarPurchaseDto of(CarPurchaseHistory history) {
        return new CarPurchaseDto(
                history.getMarketplaceCar().getCarDetails().getName(),
                history.getMarketplaceCar().getPrice(),
                history.getUser().getName(),
                history.getMarketplaceCar().getCarDetails().getLicensePlate(),
                history.getMarketplaceCar().getMainImage(),
                history.getApprovedAt(),
                history.getMarketplaceCar().getStatus(),
                history.getId()
        );
    }
}
