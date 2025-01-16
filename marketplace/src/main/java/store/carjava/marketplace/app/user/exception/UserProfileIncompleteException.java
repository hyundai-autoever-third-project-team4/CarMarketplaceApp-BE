package store.carjava.marketplace.app.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import store.carjava.marketplace.common.exception.BusinessException;

@ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
public class UserProfileIncompleteException extends BusinessException {
    public UserProfileIncompleteException() {
        super("사용자의 추가 정보(이름, 전화번호, 주소)가 필요합니다.");

    }
}