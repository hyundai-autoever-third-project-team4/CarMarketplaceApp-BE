package store.carjava.marketplace.app.car_sales_history.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.user.entity.User;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "car_sales_history")
public class CarSalesHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "marketplace_car_id",nullable = false)
    MarketplaceCar marketplaceCar;


    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    User user;
}