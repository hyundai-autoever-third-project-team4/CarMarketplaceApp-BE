package store.carjava.marketplace.app.marketplace_car.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import store.carjava.marketplace.common.exception.BusinessException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VehicleTypeNotFoundException extends BusinessException {
    public VehicleTypeNotFoundException() {super("[ERROR] 해당하는 차량 타입은 없습니다. 입력 가능한 타입: 승용, SUV, 승합, EV");}
}
