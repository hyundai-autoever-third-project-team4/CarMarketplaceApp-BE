package store.carjava.marketplace.firebase.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import store.carjava.marketplace.firebase.entity.FCMToken;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface FCMTokenRepository extends JpaRepository<FCMToken, Long> {
    Optional<FCMToken> findByUserIdAndFcmToken(Long userId, String token);
    void deleteByFcmToken(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM FCMToken fcm WHERE fcm.updatedAt < :dateTime")
    void deleteOldTokens(LocalDateTime dateTime);
}
