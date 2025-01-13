package store.carjava.marketplace.app.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.carjava.marketplace.app.like.entity.Like;
import store.carjava.marketplace.app.user.entity.User;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    // 찜 여부 확인
    boolean existsByMarketplaceCarIdAndUserId(String carId, Long userId);

    //찜 삭제
    void deleteByMarketplaceCarIdAndUserId(String marketplaceCarId, Long userId);

    //차량 토탈 찜 수
    Long countByMarketplaceCarId(String marketplaceCarId);

    //유저가 찜한 차량 조회
    List<Like> findByUser(User user);
    //유저가 찜한 차량 수 카운트
    Long countByUser(User user);

}
