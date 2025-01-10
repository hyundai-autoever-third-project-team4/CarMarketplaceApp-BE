package store.carjava.marketplace.app.marketplace_car_extra_option.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "marketplace_car_extra_option")
public class MarketplaceCarExtraOption {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "marketplace_car_id")
    MarketplaceCar marketplaceCar;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price" , nullable = false)
    private int price;

}
