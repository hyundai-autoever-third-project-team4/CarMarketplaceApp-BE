package store.carjava.marketplace.app.car_purchase_history.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import store.carjava.marketplace.app.car_purchase_history.entity.CarPurchaseHistory;
import store.carjava.marketplace.app.user.entity.User;

import java.util.List;

public interface CarPurchaseHistoryRepository extends JpaRepository<CarPurchaseHistory, Long> {

    List<CarPurchaseHistory> findByUserOrderByIdDesc(User user);

    @Query("SELECT COALESCE(SUM(c.totalAmount), 0) FROM CarPurchaseHistory c")
    Long findTotalAmountSum();

    List<CarPurchaseHistory> findAllByMarketplaceCarStatus(String marketplaceCarStatus);
}