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
        MarketplaceCarRecommandInfoDto normal, less, over;


        Optional<MarketplaceCar> carExist = marketplaceCarRepository.findTopByPriceLessThanEqualOrderByPriceDesc(budget);
        if (carExist.isEmpty()) {
            // 예산이 너무 적은 경우 - 구매할 수 있는 차량이 없는 경우
            normal = MarketplaceCarRecommandInfoDto.of("해당하는 차량이 없습니다.");
            less = MarketplaceCarRecommandInfoDto.of("더 저렴한 차량이 없습니다.");
        }
        else{
            // 적정 가격 추천
            long budgetLow = (long) (budget * 0.9);
            long budgetHigh = (long) (budget * 0.95);

            List<MarketplaceCar> carList = marketplaceCarRepository.findMarketplaceCarList(budgetLow, budgetHigh, request.vehicleType());

            // 적정 가격 범위 내에 차가 없거나 1개만 존재하는 경우
            if (carList.size() < 2) {
                long normalPrice;

                if (carList.isEmpty()) {
                    normal = MarketplaceCarRecommandInfoDto.of(carExist.get());
                    normalPrice = carExist.get().getPrice();
                } else {
                    normal = MarketplaceCarRecommandInfoDto.of(carList.get(0));
                    normalPrice = carList.get(0).getPrice();
                }

                Optional<MarketplaceCar> lessCarExist = marketplaceCarRepository.findTopByPriceLessThanEqualOrderByPriceDesc(normalPrice - 1);
                less = lessCarExist.map(MarketplaceCarRecommandInfoDto::of).orElseGet(() -> MarketplaceCarRecommandInfoDto.of("더 저렴한 차량이 없습니다."));
            }
            else{
                normal = MarketplaceCarRecommandInfoDto.of(carList.get(0));
                less = MarketplaceCarRecommandInfoDto.of(carList.get(carList.size() - 1));
            }
        }

        log.info("선택한 적정 차 이름 {}", normal.name());
        log.info("선택한 저렴 차 이름 {}", less.name());

        // 예산이 너무 큰 경우 - 비싼 차량 추천 불가
        /*boolean overCarExist = marketplaceCarRepository.existsByPriceGreaterThan(budget);
        if(!overCarExist){
            over = MarketplaceCarRecommandInfoDto.of("더 비싼 차량이 없습니다.");
        }
        else{

        }*/

        return;
    }
}
