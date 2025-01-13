package store.carjava.marketplace.app.marketplace_car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;

public interface MarketplaceCarRepository extends JpaRepository<MarketplaceCar, String> {
}
