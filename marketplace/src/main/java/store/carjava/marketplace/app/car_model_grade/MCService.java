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
            long budgetLow = (long) (budget * 0.9);
            long budgetHigh = (long) (budget * 0.95);

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

        log.info("선택한 적정 차 이름:{} / 타입:{} / 주행거리:{} / 모델:{} / 가격:{}", normal.getCarDetails().getName(), normal.getCarDetails().getVehicleType(), normal.getCarDetails().getMileage(), normal.getCarDetails().getModel(), normal.getPrice());
        log.info("선택한 저렴 차 이름:{} / 타입:{} / 주행거리:{} / 모델:{} / 가격:{}", less.getCarDetails().getName(), less.getCarDetails().getVehicleType(), less.getCarDetails().getMileage(), less.getCarDetails().getModel(), less.getPrice());

        // 초과 추천
        Optional<MarketplaceCar> overExist = marketplaceCarRepository.findTopByPriceGreaterThanOrderByPrice(budget);
        if (overExist.isEmpty()) {
            over = null;
        }
        else if (normal == null) {
            over = overExist.get();
        }
        else{
            over = marketplaceCarRepository.findMarketplaceCarOverPrice(budget, vehicle, normal);
            if (over != null) {
                log.info("선택한 비싼 차 이름:{} 타입:{} 주행거리:{} 모델:{} / 가격:{}", over.getCarDetails().getName(), over.getCarDetails().getVehicleType(), over.getCarDetails().getMileage(), over.getCarDetails().getModel(), over.getPrice());
            }else log.info("선택된 차 없음~~");

           if(over == null){ over = overExist.get();}
        }

        return;
    }
}
