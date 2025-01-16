package store.carjava.marketplace.app.marketplace_car.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import store.carjava.marketplace.common.exception.BusinessException;

@ResponseStatus(HttpStatus.NOT_FOUND)  // 404 상태 코드
public class BaseCarNotFoundException extends BusinessException {

    public BaseCarNotFoundException() {
        super("해당 번호판과 이름에 해당하는 차량이 존재하지 않습니다.");
    }
}
