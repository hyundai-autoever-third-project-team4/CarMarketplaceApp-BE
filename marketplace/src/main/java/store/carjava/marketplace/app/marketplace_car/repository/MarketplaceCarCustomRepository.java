
package store.carjava.marketplace.app.marketplace_car.repository;

import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;

import java.util.List;

public interface MarketplaceCarCustomRepository {
    List<MarketplaceCar> filterCars(String model, String fuelType, String brand, String colorType ,
                                    String driveType, String licensePlate, String transmission, String vehicleType,
                                    Integer modelYear, Integer seatingCapacity, Long minPrice, Long maxPrice,
                                    Integer minMileage, Integer maxMileage, Integer minModelYear, Integer maxModelYear,
                                    List<Long> optionIds, String testDriveCenterName, String status, Integer minEngineCapacity,
                                    Integer maxEngineCapacity, String name, String sortOrder
                                    );
}