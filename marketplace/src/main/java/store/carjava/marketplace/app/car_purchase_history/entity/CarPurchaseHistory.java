package store.carjava.marketplace.app.car_purchase_history.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "car_purchase_history")
public class CarPurchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;

    @Column(name = "order_name", nullable = false)
    private String orderName;

    @Column(name = "approved_at", nullable = false)
    private LocalDateTime approvedAt; // 결제일

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt; // 확정 날짜

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "total_amount", nullable = false)
    private Long totalAmount;

    @Column(name = "supplied_amount", nullable = false)
    private Long suppliedAmount;

    @Column(name = "vat", nullable = false)
    private Long vat;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "marketplace_car_id", nullable = false)
    private MarketplaceCar marketplaceCar;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
