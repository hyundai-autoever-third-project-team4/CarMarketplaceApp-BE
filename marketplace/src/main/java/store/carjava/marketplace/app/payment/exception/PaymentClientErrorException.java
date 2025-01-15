package store.carjava.marketplace.app.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import store.carjava.marketplace.common.exception.BusinessException;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PaymentClientErrorException extends BusinessException {
    public PaymentClientErrorException() {
        super("[ERROR] 클라이언트 요청 오류");
    }
}