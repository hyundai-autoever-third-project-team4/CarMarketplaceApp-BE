package store.carjava.marketplace.app.reservation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import store.carjava.marketplace.common.exception.BusinessException;

@ResponseStatus(HttpStatus.CONFLICT)
public class ReservationAlreadyExistsException extends BusinessException {
    public ReservationAlreadyExistsException() {
        super("[ERROR] 해당 날짜에는 이미 예약이 존재합니다.");
    }
}
