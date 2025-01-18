package store.carjava.marketplace.web.admin.dto;

import store.carjava.marketplace.app.car_purchase_history.entity.CarPurchaseHistory;
import store.carjava.marketplace.app.car_sales_history.entity.CarSalesHistory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CarSellDto(
        String carId,
        String carName,
        Long price,
        String userName,
        String licensePlate,
        String mainImage,
        LocalDate requestDate,
        String status,
        Long saleId

) {
    public static CarSellDto of(CarSalesHistory history) {
        return new CarSellDto(
                history.getMarketplaceCar().getId(),
                history.getMarketplaceCar().getCarDetails().getName(),
                history.getMarketplaceCar().getPrice(),
                history.getUser().getName(),
                history.getMarketplaceCar().getCarDetails().getLicensePlate(),
                history.getMarketplaceCar().getMainImage(),
                history.getMarketplaceCar().getMarketplaceRegistrationDate(),
                history.getMarketplaceCar().getStatus(),
                history.getId()
        );
    }
}
