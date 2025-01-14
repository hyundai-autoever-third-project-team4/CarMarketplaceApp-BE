package store.carjava.marketplace.app.review.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import store.carjava.marketplace.common.exception.BusinessException;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ReviewAlreadyExistsException extends BusinessException {
    public ReviewAlreadyExistsException() {
        super("[ERROR] 이미 해당 차량에 대한 리뷰를 작성했습니다.");
    }
}
