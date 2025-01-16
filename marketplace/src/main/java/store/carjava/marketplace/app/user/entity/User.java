package store.carjava.marketplace.app.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import store.carjava.marketplace.app.car_purchase_history.entity.CarPurchaseHistory;
import store.carjava.marketplace.app.car_sales_history.entity.CarSalesHistory;
import store.carjava.marketplace.app.like.entity.Like;
import store.carjava.marketplace.app.reservation.entity.Reservation;
import store.carjava.marketplace.app.review.entity.Review;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CarSalesHistory> carSalesHistories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CarPurchaseHistory> carPurchaseHistories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    /**
     * 업데이트를 수행하면서 새로운 User 객체를 반환
     */
    public User updateRole(String newRole) {
        return User.builder()
                .id(this.id)         // 기존 ID 유지
                .email(this.email)   // 기존 이메일 유지
                .role(newRole)       // 새로운 역할로 업데이트
                .build();
    }

    // 추가 정보 입력 여부 확인
    public boolean isProfileComplete() {
        return !StringUtils.isEmpty(name) &&
                !StringUtils.isEmpty(phone) &&
                !StringUtils.isEmpty(address);
    }

    // 유저 정보 업데이트
    public User updateProfile(String name, String phone, String address) {
        return User.builder()
                .id(this.id)
                .email(this.email)
                .role(this.role)
                .name(name)
                .phone(phone)
                .address(address)
                .build();
    }

}
