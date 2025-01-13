package store.carjava.marketplace.app.car_model_grade;

import org.springframework.data.jpa.repository.JpaRepository;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;

import java.util.Optional;

public interface MCRepository extends JpaRepository<MarketplaceCar, String>, MCRepositoryCustom {
    Optional<MarketplaceCar> findTopByPriceLessThanEqualOrderByPriceDesc(Long price);

    boolean existsByPriceGreaterThan(Long price);
}
