package store.carjava.marketplace.app.car_model_grade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MCService {
    private final MCRepository marketplaceCarRepository;

    public MarketplaceCarRecommandListResponse getRecommand(MarketplaceCarRecommandRequest request) {
        long budget = request.budget() * 10000;
        String vehicle = request.vehicleType();
        MarketplaceCar normal, less, over;

        // 적정, 저렴 추천
        List<MarketplaceCar> carExist = marketplaceCarRepository.findTop2ByCarDetails_VehicleTypeAndPriceLessThanEqualOrderByPriceDesc(vehicle, budget);
        if (carExist.isEmpty()) {
            // 예산 내 차가 0대인 경우 - 너무 적은 예산
            normal = null;
            less = null;
        } else if (carExist.size() == 1) {
            // 예산 내 차가 1대인 경우
            normal = carExist.get(0);
            less = null;
        } else{
            // 예산 내 차가 여러 대인 경우
            long budgetLow = (long) (budget * 0.92);
            long budgetHigh = (long) (budget * 0.97);

            List<MarketplaceCar> carList = marketplaceCarRepository.findMarketplaceCarProperList(budgetLow, budgetHigh, vehicle);

            // 적정 가격 범위 내에 차가 없거나 1개만 존재하는 경우
            if (carList.size() < 2) {
                long normalPrice;

                // 범위 내 차가 없음
                if (carList.isEmpty()) {
                    normal = carExist.get(0);
                    normalPrice = carExist.get(0).getPrice();
                } else {    // 범위 내 차가 1대
                    normal = carList.get(0);
                    normalPrice = carList.get(0).getPrice();
                }
                Optional<MarketplaceCar> lessCarExist = marketplaceCarRepository.findTopByCarDetails_VehicleTypeAndPriceLessThanEqualOrderByPriceDesc(vehicle, normalPrice - 1);
                less = lessCarExist.orElse(null);
            }
            else{
                normal = carList.get(0);
                less = carList.get(carList.size() - 1);
            }
        }

        // 초과 추천
        Optional<MarketplaceCar> overExist = marketplaceCarRepository.findTopByPriceGreaterThanOrderByPrice(budget);
        if (overExist.isEmpty()) {
            over = null;
        }
        else if (normal == null) {
            over = overExist.get();
        }
        else{
            // 고급 모델 차량
            MarketplaceCar upgrade1 = marketplaceCarRepository.findUpgradeModelCarOverPrice(budget, vehicle, normal);
            // 동일 모델 차량, 짧은 주행거리 + 최신 연식
            MarketplaceCar upgrade2 = marketplaceCarRepository.findCarMoreOptionOverPrice(budget, vehicle, normal);

            if (upgrade1 != null){
                log.info("고급 모델 후보: {} / 가격: {} / 주행거리: {}", upgrade1.getCarDetails().getName(), upgrade1.getPrice(), upgrade1.getCarDetails().getMileage());
            }
            if (upgrade2 != null){
                log.info("동일 모델 후보: {} / 가격: {} / 주행거리: {}", upgrade2.getCarDetails().getName(), upgrade2.getPrice(), upgrade2.getCarDetails().getMileage());
            }

            if (upgrade1 != null && upgrade2 != null) {
                if (upgrade1.getPrice() < upgrade2.getPrice()) { over = upgrade1; }
                else { over = upgrade2; }
            }
            else if (upgrade1 != null) { over = upgrade1; }
            else if (upgrade2 != null) { over = upgrade2; }
            else { over = overExist.get(); }
        }

        return MarketplaceCarRecommandListResponse.of(
                MarketplaceCarRecommandInfoDto.of(normal),
                MarketplaceCarRecommandInfoDto.of(less),
                MarketplaceCarRecommandInfoDto.of(over));
    }
}
