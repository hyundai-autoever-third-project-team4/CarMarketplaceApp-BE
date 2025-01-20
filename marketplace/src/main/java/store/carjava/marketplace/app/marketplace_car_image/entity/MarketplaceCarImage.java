package store.carjava.marketplace.app.marketplace_car_image.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "marketplace_car_image")
public class MarketplaceCarImage {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "marketplace_car_id")
    MarketplaceCar marketplaceCar;

    @Column(name = "image_url", nullable = false, columnDefinition = "TEXT")
    private String imageUrl;

}