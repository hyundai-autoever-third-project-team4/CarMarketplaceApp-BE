package store.carjava.marketplace.app.test_drive_center.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import store.carjava.marketplace.common.exception.BusinessException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TestDriverCenterIdNotFoundException extends BusinessException {

    public TestDriverCenterIdNotFoundException() {
        super("[ERROR] 해당 시승소 id는 존재하지 않습니다.");
    }
}
