package store.carjava.marketplace.app.review.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import store.carjava.marketplace.common.exception.BusinessException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReviewIdNotFoundException extends BusinessException {

    public ReviewIdNotFoundException() {
        super("[ERROR] 해당 review id는 존재하지 않습니다.");
    }
}


