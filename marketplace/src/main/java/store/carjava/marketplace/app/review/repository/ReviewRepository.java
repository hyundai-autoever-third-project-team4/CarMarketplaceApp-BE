package store.carjava.marketplace.app.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.carjava.marketplace.app.review.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {



    //특정 사용자의 모든 리뷰 조회
    List<Review> findByUserId(Long userId);

    //특정 모델의 리뷰 조회 ex) g70, 그랜저...
    List<Review> findByMarketplaceCarId(String carId);
}
