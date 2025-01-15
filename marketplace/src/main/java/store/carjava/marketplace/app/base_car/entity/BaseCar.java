package store.carjava.marketplace.app.base_car.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.carjava.marketplace.app.base_car_option.entity.BaseCarOption;
import store.carjava.marketplace.app.vo.CarDetails;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "base_car") // 데이터베이스 테이블 이름 지정
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class BaseCar {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id; // 차량 ID (Primary Key, UUID)

    @Column(name = "owner_name", nullable = false)
    private String ownerName; // 차량 소유자 이름.

    @Embedded
    private CarDetails carDetails; //공통필드 클래스

    @OneToMany(mappedBy = "baseCar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BaseCarOption> baseCarOptions = new ArrayList<>();

    @Column(name = "main_image", nullable = false)
    private String mainImage;
}