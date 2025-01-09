package store.carjava.marketplace.app.reservation.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.user.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "reservation")
public class Reservation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "marketplace_car_id")
    MarketplaceCar marketplaceCar;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "reservation_date",nullable = false)
    private LocalDate reservationDate;

    @Column(name = "reservation_time",nullable = false)
    private LocalDateTime reservationTime;

    @Column(name = "created_at" , nullable = false)
    private LocalDateTime createdAt;
}
