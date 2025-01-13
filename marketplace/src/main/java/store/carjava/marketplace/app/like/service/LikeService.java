package store.carjava.marketplace.app.like.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.app.like.dto.LikeResponse;
import store.carjava.marketplace.app.like.entity.Like;
import store.carjava.marketplace.app.like.repository.LikeRepository;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;
import store.carjava.marketplace.app.marketplace_car.repository.MarketplaceCarRepository;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.common.util.UserNotAuthenticatedException;
import store.carjava.marketplace.common.util.UserResolver;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserResolver userResolver;
    private final MarketplaceCarRepository carRepository;

    public LikeResponse toggleLike(String carId) {
        // 1. 로그인한 유저 정보 가져오기.
        User currentUser = userResolver.getCurrentUser();
        if (currentUser == null) {
            throw new UserNotAuthenticatedException();
        }

        // 2. 차량 정보 가져오기
        MarketplaceCar car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id:"));

        boolean isLiked;
        if (likeRepository.existsByMarketplaceCarIdAndUserId(carId, currentUser.getId())) {
            // 찜이 되어있으면 삭제
            likeRepository.deleteByMarketplaceCarIdAndUserId(carId, currentUser.getId());
            isLiked = false;
        }
        else {
            //찜이 안되어있으면 생성.
            Like like = Like.builder()
                    .marketplaceCar(car)
                    .user(currentUser)
                    .build();
            likeRepository.save(like);
            isLiked = true;
        }
        Long totalLikeCount = likeRepository.countByMarketplaceCarId(carId);

        return new LikeResponse(
                carId, currentUser.getId(), isLiked, totalLikeCount
        );
    }
}
