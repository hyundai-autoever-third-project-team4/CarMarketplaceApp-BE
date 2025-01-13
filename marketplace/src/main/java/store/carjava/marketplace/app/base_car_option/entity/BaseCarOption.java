package store.carjava.marketplace.app.base_car_option.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.carjava.marketplace.app.base_car.entity.BaseCar;
import store.carjava.marketplace.app.option.entity.Option;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "base_car_option")
public class BaseCarOption {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "base_car_id")
    BaseCar baseCar;

    @ManyToOne
    @JoinColumn(name = "option_id")
    Option option;

    @Column(name = "is_present")
    private Boolean isPresent;
}