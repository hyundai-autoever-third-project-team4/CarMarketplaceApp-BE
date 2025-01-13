package store.carjava.marketplace.app.marketplace_car.exception;

import store.carjava.marketplace.common.exception.BusinessException;

public class MaxPriceLessThanMinPriceException extends BusinessException {

    public MaxPriceLessThanMinPriceException() {
        super("[ERROR] 최대가격은 최소 가격보다 작을 수 없습니다.");
    }
}
