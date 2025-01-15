
package store.carjava.marketplace.app.marketplace_car.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;

import java.util.List;

public interface MarketplaceCarCustomRepository {
    Page<MarketplaceCar> filterCars(List<String> models, List<String> fuelType, String brand, List<String> colorType ,
                                    String driveType, String licensePlate, String transmission, List<String> vehicleType,
                                    Integer modelYear, Integer seatingCapacity, Long minPrice, Long maxPrice,
                                    Integer minMileage, Integer maxMileage, Integer minModelYear, Integer maxModelYear,
                                    List<Long> optionIds, String testDriveCenterName, String status, Integer minEngineCapacity,
                                    Integer maxEngineCapacity, String name, String sortOrder , Pageable pageable
                                    );

    List<MarketplaceCar> findMarketplaceCarProperList(long budgetLow, long budgetHigh, List<String> vehicle);
    MarketplaceCar findUpgradeModelCarOverPrice(long budget, List<String> vehicle, MarketplaceCar marketplaceCar);
    MarketplaceCar findCarMoreOptionOverPrice(long budget, MarketplaceCar marketplaceCar);
}