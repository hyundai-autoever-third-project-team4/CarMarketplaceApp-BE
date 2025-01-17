package store.carjava.marketplace.firebase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import store.carjava.marketplace.common.exception.BusinessException;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class FirebaseInitializeFailed extends BusinessException {
    public FirebaseInitializeFailed() {
        super("[Error] firebase admin sdk 초기화에 실패했습니다.");
    }
}
