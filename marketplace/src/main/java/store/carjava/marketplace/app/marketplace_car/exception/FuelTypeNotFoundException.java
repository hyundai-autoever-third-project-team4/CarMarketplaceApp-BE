package store.carjava.marketplace.app.marketplace_car.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import store.carjava.marketplace.common.exception.BusinessException;

@ResponseStatus(HttpStatus.NOT_FOUND)  // 404 상태 코드
public class FuelTypeNotFoundException extends BusinessException {
    public FuelTypeNotFoundException(String fuelType) {
        super("[ERROR] 해당 연료타입 '" + fuelType + "'이 존재하지 않습니다.");
    }
}
