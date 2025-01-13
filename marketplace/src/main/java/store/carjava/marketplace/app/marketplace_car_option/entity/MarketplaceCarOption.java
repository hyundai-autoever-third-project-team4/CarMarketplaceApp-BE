package store.carjava.marketplace.app.marketplace_car_option.entity;


import jakarta.persistence.*;
import lombok.*;
import store.carjava.marketplace.app.base_car.entity.BaseCar;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.option.entity.Option;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "marketplace_car_option")
public class MarketplaceCarOption {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id")
    MarketplaceCar marketplaceCar;

    @ManyToOne
    @JoinColumn(name = "option_id")
    Option option;

    @Column(name = "is_present")
    private Boolean isPresent;
}