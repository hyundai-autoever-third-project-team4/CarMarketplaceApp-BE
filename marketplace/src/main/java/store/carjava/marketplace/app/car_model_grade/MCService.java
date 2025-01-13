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

    public void getRecommand(MarketplaceCarRecommandRequest request) {
        long budget = request.budget() * 10000;
        String vehicle = request.vehicleType();
        MarketplaceCarRecommandInfoDto normal, less, over;

        // 적정, 저렴 추천
        List<MarketplaceCar> carExist = marketplaceCarRepository.findTop2ByCarDetails_VehicleTypeAndPriceLessThanEqualOrderByPriceDesc(vehicle, budget);
        if (carExist.isEmpty()) {
            // 예산 내 차가 0대인 경우 - 너무 적은 예산
            normal = MarketplaceCarRecommandInfoDto.of("해당하는 차량이 없습니다.");
            less = MarketplaceCarRecommandInfoDto.of("더 저렴한 차량이 없습니다.");
        } else if (carExist.size() == 1) {
            // 예산 내 차가 1대인 경우
            normal = MarketplaceCarRecommandInfoDto.of(carExist.get(0));
            less = MarketplaceCarRecommandInfoDto.of("더 저렴한 차량이 없습니다.");
        } else{
            // 예산 내 차가 여러 대인 경우
            long budgetLow = (long) (budget * 0.9);
            long budgetHigh = (long) (budget * 0.95);

            List<MarketplaceCar> carList = marketplaceCarRepository.findMarketplaceCarList(budgetLow, budgetHigh, vehicle);

            // 적정 가격 범위 내에 차가 없거나 1개만 존재하는 경우
            if (carList.size() < 2) {
                long normalPrice;

                // 범위 내 차가 없음
                if (carList.isEmpty()) {
                    normal = MarketplaceCarRecommandInfoDto.of(carExist.get(0));
                    normalPrice = carExist.get(0).getPrice();
                } else {    // 범위 내 차가 1대
                    normal = MarketplaceCarRecommandInfoDto.of(carList.get(0));
                    normalPrice = carList.get(0).getPrice();
                }
                Optional<MarketplaceCar> lessCarExist = marketplaceCarRepository.findTopByCarDetails_VehicleTypeAndPriceLessThanEqualOrderByPriceDesc(vehicle, normalPrice - 1);
                less = lessCarExist.map(MarketplaceCarRecommandInfoDto::of).orElseGet(() -> MarketplaceCarRecommandInfoDto.of("더 저렴한 차량이 없습니다."));
            }
            else{
                normal = MarketplaceCarRecommandInfoDto.of(carList.get(0));
                less = MarketplaceCarRecommandInfoDto.of(carList.get(carList.size() - 1));
            }
        }

        log.info("선택한 적정 차 이름 {}, 가격 {}", normal.name(), normal.price());
        log.info("선택한 저렴 차 이름 {}, 가격 {}", less.name(), less.price());

        // 초과 추천


        return;
    }
}
