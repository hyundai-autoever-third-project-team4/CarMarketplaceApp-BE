package store.carjava.marketplace.app.car_model_grade;

import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;

import java.util.List;

public interface MCRepositoryCustom {
    List<MarketplaceCar> findMarketplaceCarProperList(long budgetLow, long budgetHigh, String vehicle);
    MarketplaceCar findUpgradeModelCarOverPrice(long budget, String vehicle, MarketplaceCar marketplaceCar);
    MarketplaceCar findCarMoreOptionOverPrice(long budget, String vehicle, MarketplaceCar marketplaceCar);
}
