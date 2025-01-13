package store.carjava.marketplace.app.marketplace_car.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;

@Repository
public interface MarketplaceCarRepository extends JpaRepository<MarketplaceCar, String>, MarketplaceCarCustomRepository{

}