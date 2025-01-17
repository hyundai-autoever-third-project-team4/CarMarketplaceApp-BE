package store.carjava.marketplace.app.car_sales_history.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import store.carjava.marketplace.app.car_sales_history.entity.CarSalesHistory;
import store.carjava.marketplace.app.user.entity.User;

import java.util.List;

public interface CarSalesHistoryRepository extends JpaRepository<CarSalesHistory,Long> {

    List<CarSalesHistory> findByUserOrderByIdDesc(User user);

    List<CarSalesHistory> findAllByMarketplaceCarStatus(String marketplaceCarStatus);
}
