package store.carjava.marketplace.app.car_sales_history.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import store.carjava.marketplace.app.car_sales_history.entity.CarSalesHistory;

public interface CarSalseHistoryRepository extends JpaRepository<CarSalesHistory,Long> {
}
