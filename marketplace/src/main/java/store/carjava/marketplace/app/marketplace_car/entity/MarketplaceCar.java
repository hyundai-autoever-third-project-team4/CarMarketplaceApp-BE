package store.carjava.marketplace.app.marketplace_car.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.carjava.marketplace.app.marketplace_car_option.entity.MarketplaceCarOption;
import store.carjava.marketplace.app.car_purchase_history.entity.CarPurchaseHistory;
import store.carjava.marketplace.app.car_sales_history.entity.CarSalesHistory;
import store.carjava.marketplace.app.like.entity.Like;
import store.carjava.marketplace.app.marketplace_car_extra_option.entity.MarketplaceCarExtraOption;
import store.carjava.marketplace.app.marketplace_car_image.entity.MarketplaceCarImage;
import store.carjava.marketplace.app.reservation.entity.Reservation;
import store.carjava.marketplace.app.test_drive_center.entity.TestDriveCenter;
import store.carjava.marketplace.app.vo.CarDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "marketplace_car")
public class MarketplaceCar {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id; // 차량 ID

    @Embedded
    private CarDetails carDetails; //공통필드 클래스

    /*@Column(name = "brand", nullable = false)
    private String brand; // 차량 브랜드

    @Column(name = "model", nullable = false)
    private String model; // 차량 모델

    @Column(name = "drive_type", nullable = false)
    private String driveType; // 차량 드라이브 타입

    @Column(name = "engine_capacity")
    private Integer engineCapacity; // 차량 CC

    @Column(name = "exterior_color", nullable = false)
    private String exteriorColor; // 외부 색상

    @Column(name = "interior_color", nullable = false)
    private String interiorColor; // 내부 색상

    @Column(name = "color_type", nullable = false)
    private String colorType; // 외부 색상 타입

    @Column(name = "fuel_type", nullable = false)
    private String fuelType; // 연료 타입

    @Column(name = "license_plate", nullable = false)
    private String licensePlate; // 차량 번호판

    @Column(name = "mileage", nullable = false)
    private Integer mileage; // 주행 거리

    @Column(name = "model_year", nullable = false)
    private Integer modelYear; // 모델 연식

    @Column(name = "name", nullable = false)
    private String name; // 차량 전체 이름

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate; // 차량 등록일자

    @Column(name = "seating_capacity", nullable = false)
    private Integer seatingCapacity; // 탑승 인원

    @Column(name = "transmission")
    private String transmission; // 변속기

    @Column(name = "vehicle_type", nullable = false)
    private String vehicleType; // 차량 유형*/

    @ManyToOne
    @JoinColumn(name = "test_driver_center_id")
    TestDriveCenter testDriveCenter;

    @Column(name = "price", nullable = false)
    private Long price; // 판매 가격

    @Column(name = "marketplace_registration_date", nullable = false)
    private LocalDate marketplaceRegistrationDate; // 중고차 사이트 등록일

    @Column(name = "status", nullable = false)
    private String status; // 차량 상태

    @Column(name = "main_image")
    private String mainImage; // 메인 이미지

    @OneToMany(mappedBy = "marketplaceCar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MarketplaceCarImage> marketplaceCarImages = new ArrayList<>();

    @OneToMany(mappedBy = "marketplaceCar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "marketplaceCar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MarketplaceCarExtraOption> marketplaceCarExtraOptions = new ArrayList<>();

    @OneToMany(mappedBy = "marketplaceCar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarPurchaseHistory> CarPurchaseHistories = new ArrayList<>();

    @OneToMany(mappedBy = "marketplaceCar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "marketplaceCar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarSalesHistory> carSalesHistories = new ArrayList<>();

    @OneToMany(mappedBy = "marketplaceCar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MarketplaceCarOption> carOptions = new ArrayList<>();

}
