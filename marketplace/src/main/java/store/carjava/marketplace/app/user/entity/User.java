package store.carjava.marketplace.app.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String role;

    // 추가 정보 (nullable 허용)
    @Column
    private String name;

    @Column
    private String phone;

    @Column
    private String address;

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
}
