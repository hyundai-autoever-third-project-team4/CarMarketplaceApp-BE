package store.carjava.marketplace.app.test_drive_center.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "test_drive_center")
public class TestDriveCenter {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "testDriveCenter", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MarketplaceCar> marketplaceCars = new ArrayList<>();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

}