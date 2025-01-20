package store.carjava.marketplace.app.marketplace_car.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import store.carjava.marketplace.app.marketplace_car.entity.MarketplaceCar;

@Getter
public class CarSellEvent extends ApplicationEvent {
    private final String id;
    private final String name;
    private final String model;
    // 알람에 필요한거. 차 모델, 차 이름

    public CarSellEvent(Object source, MarketplaceCar marketplaceCar) {
        super(source);
        this.id = marketplaceCar.getId();
        this.name = marketplaceCar.getCarDetails().getName();
        this.model = marketplaceCar.getCarDetails().getModel();
    }
}
