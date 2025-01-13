package store.carjava.marketplace.app.marketplace_car.exception;

import store.carjava.marketplace.common.exception.BusinessException;

public class MinPriceExceedsMaxPriceException extends BusinessException {
    public MinPriceExceedsMaxPriceException(){
        super("[ERROR] 최소 가격은 최대 가격보다 작아야 합니다.");
    }
}
