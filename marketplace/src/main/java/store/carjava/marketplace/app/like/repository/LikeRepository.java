package store.carjava.marketplace.app.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.carjava.marketplace.app.like.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByMarketplaceCarIdAndUserId(String carId, Long userId);
    void deleteByMarketplaceCarIdAndUserId(String marketplaceCarId, Long userId);
    Long countByMarketplaceCarId(String marketplaceCarId);
}
