package store.carjava.marketplace.app.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import store.carjava.marketplace.common.exception.BusinessException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserIdNotFoundException extends BusinessException {

    public UserIdNotFoundException() {
        super("[ERROR] 해당 user id는 존재하지 않습니다.");
    }
}
