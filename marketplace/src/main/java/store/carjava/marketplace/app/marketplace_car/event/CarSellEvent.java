package store.carjava.marketplace.app.marketplace_car.event;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;

import static store.carjava.marketplace.app.marketplace_car.entity.QMarketplaceCar.marketplaceCar;

@Slf4j
@Getter
public class CarSellEvent extends ApplicationEvent {
    private final String id;
    private final String name;
    private final String model;
    // 알람에 필요한거. 차 모델, 차 이름




    public CarSellEvent(Object source, MarketplaceCar marketplaceCar) {
        super(source);

        // 이벤트 생성 시점 로그
        log.info("이벤트 생성 시 차량 정보: id={}, model={}, name={}",
                marketplaceCar.getId(),
                marketplaceCar.getCarDetails() != null ? marketplaceCar.getCarDetails().getModel() : "null",
                marketplaceCar.getCarDetails() != null ? marketplaceCar.getCarDetails().getName() : "null");

        this.id = marketplaceCar.getId();
        this.name = marketplaceCar.getCarDetails().getName();
        this.model = marketplaceCar.getCarDetails().getModel();
    }
}
