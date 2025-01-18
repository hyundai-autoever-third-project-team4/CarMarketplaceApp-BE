package store.carjava.marketplace.marketplace_car.builder;


import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;

public class MarketplaceCarTestBuilder {
    public static MarketplaceCar build(String id, Long price, String status) {
        return MarketplaceCar.builder()
                .id(id)
                .price(price)
                .status(status)
                .build();
    }
}