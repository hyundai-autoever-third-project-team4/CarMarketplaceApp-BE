package store.carjava.marketplace.app.marketplace_car.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import store.carjava.marketplace.common.exception.BusinessException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CarNotFoundException extends BusinessException {
    public CarNotFoundException() {
        super("[ERROR] 해당 조건에 맞는 차량이 존재하지 않습니다");
    }
}
