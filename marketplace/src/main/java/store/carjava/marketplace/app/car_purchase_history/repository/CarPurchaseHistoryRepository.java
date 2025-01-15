package store.carjava.marketplace.app.car_purchase_history.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.carjava.marketplace.app.car_purchase_history.entity.CarPurchaseHistory;

public interface CarPurchaseHistoryRepository extends JpaRepository<CarPurchaseHistory, Long> {

}