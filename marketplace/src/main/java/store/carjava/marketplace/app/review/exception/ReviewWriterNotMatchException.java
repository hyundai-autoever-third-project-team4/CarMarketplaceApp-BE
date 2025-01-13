package store.carjava.marketplace.app.review.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import store.carjava.marketplace.common.exception.BusinessException;


@ResponseStatus(HttpStatus.FORBIDDEN)
public class ReviewWriterNotMatchException extends BusinessException {
    public ReviewWriterNotMatchException() {
        super("[ERROR] 리뷰 작성자와, 현재 작성자가 일치하지 않습니다.");
    }
}
