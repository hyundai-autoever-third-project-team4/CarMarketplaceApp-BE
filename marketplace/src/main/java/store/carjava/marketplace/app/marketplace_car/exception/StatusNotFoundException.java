package store.carjava.marketplace.app.marketplace_car.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import store.carjava.marketplace.common.exception.BusinessException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import store.carjava.marketplace.common.exception.BusinessException;

@ResponseStatus(HttpStatus.NOT_FOUND)  // 404 상태 코드
public class StatusNotFoundException extends BusinessException {
    public StatusNotFoundException(String status) {
        super("[ERROR] 해당 차량상태인 '" + status + "'가 존재하지 않습니다.");
    }
}