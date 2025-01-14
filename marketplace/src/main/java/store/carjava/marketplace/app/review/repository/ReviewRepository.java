package store.carjava.marketplace.app.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.review.entity.Review;
import store.carjava.marketplace.app.user.entity.User;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {


    boolean existsByUserAndMarketplaceCar(User user, MarketplaceCar car);

    //특정 사용자의 모든 리뷰 조회
    List<Review> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    //차량 상세에서 carId로 동종 모델 모델의 리뷰 조회. 최신순 5개 ex) g70, 그랜저...
    List<Review> findTop5ByMarketplaceCar_CarDetails_ModelOrderByCreatedAtDesc(String model);
}
