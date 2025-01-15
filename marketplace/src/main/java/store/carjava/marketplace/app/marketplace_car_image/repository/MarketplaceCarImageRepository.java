package store.carjava.marketplace.app.marketplace_car_image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.carjava.marketplace.app.marketplace_car_image.entity.MarketplaceCarImage;

public interface MarketplaceCarImageRepository extends JpaRepository<MarketplaceCarImage, Long> {
}
