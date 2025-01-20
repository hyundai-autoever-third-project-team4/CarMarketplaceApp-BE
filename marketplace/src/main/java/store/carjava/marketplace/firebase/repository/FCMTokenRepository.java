package store.carjava.marketplace.firebase.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import store.carjava.marketplace.firebase.entity.FCMToken;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface FCMTokenRepository extends JpaRepository<FCMToken, Long> {
    Optional<FCMToken> findByUserIdAndFcmToken(Long userId, String token);
    void deleteByFcmToken(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM FCMToken fcm WHERE fcm.updatedAt < :dateTime")
    void deleteOldTokens(LocalDateTime dateTime);

    @Query("SELECT fcm.fcmToken FROM FCMToken fcm WHERE fcm.user.id IN :userIds")
    List<String> findAllByUserIdIn(Set<Long> userIds);
}
