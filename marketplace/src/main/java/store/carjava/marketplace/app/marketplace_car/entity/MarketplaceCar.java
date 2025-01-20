package store.carjava.marketplace.app.marketplace_car.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
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

    //차량 상태변경
    public void updateStatus(String newStatus) {
        // 유효한 상태값인지 검증
        if (!isValidStatus(newStatus)) {
            throw new IllegalArgumentException("Invalid status: " + newStatus);
        }
        this.status = newStatus;
    }

    // 유효한 상태값 검증
    private boolean isValidStatus(String status) {
        List<String> validStatus = List.of(
                "AVAILABLE_FOR_PURCHASE",
                "PENDING_PURCHASE_APPROVAL",
                "NOT_AVAILABLE_FOR_PURCHASE",
                "PENDING_SALE",
                "SALE_APPROVED"
        );
        return validStatus.contains(status);
    }

    @OneToMany(mappedBy = "marketplaceCar", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MarketplaceCarImage> marketplaceCarImages = new ArrayList<>();

    @OneToMany(mappedBy = "marketplaceCar", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "marketplaceCar", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MarketplaceCarExtraOption> marketplaceCarExtraOptions = new ArrayList<>();

    @OneToMany(mappedBy = "marketplaceCar", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CarPurchaseHistory> CarPurchaseHistories = new ArrayList<>();

    @OneToMany(mappedBy = "marketplaceCar", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "marketplaceCar", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CarSalesHistory> carSalesHistories = new ArrayList<>();

    @OneToMany(mappedBy = "marketplaceCar", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MarketplaceCarOption> marketplaceCarOptions = new ArrayList<>();

}