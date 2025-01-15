package store.carjava.marketplace.app.car_purchase_history.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.carjava.marketplace.app.car_purchase_history.entity.CarPurchaseHistory;
import store.carjava.marketplace.app.user.entity.User;

import java.util.List;

public interface CarPurchaseHistoryRepository extends JpaRepository<CarPurchaseHistory, Long> {

    List<CarPurchaseHistory> findByUserOrderByIdDesc(User user);
}