package store.carjava.marketplace.common.security;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import store.carjava.marketplace.common.exception.BusinessException;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNotAuthenticatedException extends BusinessException {
    public UserNotAuthenticatedException() {
        super("[ERROR] 인증되지 않은 사용자 요청입니다.");
    }
}
