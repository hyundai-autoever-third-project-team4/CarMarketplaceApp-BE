package store.carjava.marketplace.firebase.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.carjava.marketplace.app.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "fcm_token")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class FCMToken {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "token", nullable = false)
    private String fcmToken;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
}