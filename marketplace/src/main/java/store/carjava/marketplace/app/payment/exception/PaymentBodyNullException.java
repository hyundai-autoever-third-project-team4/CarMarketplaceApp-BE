package store.carjava.marketplace.app.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import store.carjava.marketplace.common.exception.BusinessException;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class PaymentBodyNullException extends BusinessException {
    public PaymentBodyNullException() {
        super("[ERROR] Toss Payment 서비스 api가 응답하지 않습니다.");
    }
}