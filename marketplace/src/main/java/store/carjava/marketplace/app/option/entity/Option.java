package store.carjava.marketplace.app.option.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.carjava.marketplace.app.base_car_option.entity.BaseCarOption;
import store.carjava.marketplace.app.marketplace_car_option.entity.MarketplaceCarOption;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "`option`") // option 이 예약어이기 때문에 `로 감싸줌
public class Option {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MarketplaceCarOption> marketplaceCarOptions = new ArrayList<>();

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BaseCarOption> baseCarOptions = new ArrayList<>();

}