package store.carjava.marketplace.app.marketplace_car.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import store.carjava.marketplace.common.exception.BusinessException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MarketplaceCarIdNotFoundException extends BusinessException {
    public MarketplaceCarIdNotFoundException() {
        super("[ERROR] 해당하는 id의 중고차는 존재하지 않습니다.");
    }
}
