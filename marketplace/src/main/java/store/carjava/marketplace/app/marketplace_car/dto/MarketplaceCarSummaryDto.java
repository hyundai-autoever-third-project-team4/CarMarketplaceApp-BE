package store.carjava.marketplace.app.marketplace_car.dto;

import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;

import java.time.LocalDate;

public record MarketplaceCarSummaryDto(
        String id,
        String mainImage,
        String brand,
        String model,
        String licensePlate,
        LocalDate registrationDate,
        int mileage,
        Long price
) {
    public static MarketplaceCarSummaryDto of(MarketplaceCar marketplaceCar) {
        return new MarketplaceCarSummaryDto(
                marketplaceCar.getId(),
                marketplaceCar.getMainImage(),
                marketplaceCar.getCarDetails().getBrand(),
                marketplaceCar.getCarDetails().getModel(),
                marketplaceCar.getCarDetails().getLicensePlate(),
                marketplaceCar.getCarDetails().getRegistrationDate(),
                marketplaceCar.getCarDetails().getMileage(),
                marketplaceCar.getPrice()
        );
    }
}
