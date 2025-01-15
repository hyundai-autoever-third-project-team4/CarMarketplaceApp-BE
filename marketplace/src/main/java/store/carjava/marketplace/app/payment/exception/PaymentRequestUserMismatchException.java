package store.carjava.marketplace.app.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import store.carjava.marketplace.common.exception.BusinessException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PaymentRequestUserMismatchException extends BusinessException {
    public PaymentRequestUserMismatchException() {
        super("[ERROR] 요청 사용자와 현재 사용자가 일치하지 않습니다.");
    }
}
