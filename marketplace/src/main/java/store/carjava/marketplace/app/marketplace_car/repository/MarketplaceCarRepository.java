package store.carjava.marketplace.app.marketplace_car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.test_drive_center.entity.TestDriveCenter;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarketplaceCarRepository extends JpaRepository<MarketplaceCar, String>, MarketplaceCarCustomRepository {
    List<MarketplaceCar> findAllByTestDriveCenter(TestDriveCenter testDriveCenter);

    Optional<MarketplaceCar> findTopByCarDetails_VehicleTypeAndPriceLessThanEqualOrderByPriceDesc(String vehicle, Long price);
    List<MarketplaceCar> findTop2ByCarDetails_VehicleTypeAndPriceLessThanEqualOrderByPriceDesc(String vehicle, Long price);

    Optional<MarketplaceCar> findTopByPriceGreaterThanOrderByPrice(Long price);
}
