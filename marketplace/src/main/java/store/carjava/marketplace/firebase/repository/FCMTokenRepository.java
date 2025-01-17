package store.carjava.marketplace.firebase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.carjava.marketplace.firebase.entity.FCMToken;

@Repository
public interface FCMTokenRepository extends JpaRepository<FCMToken, Long> {

}
