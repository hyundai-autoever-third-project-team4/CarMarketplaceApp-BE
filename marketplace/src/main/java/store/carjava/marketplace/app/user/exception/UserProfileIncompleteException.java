package store.carjava.marketplace.app.user.exception;

public class UserProfileIncompleteException extends RuntimeException {
    public UserProfileIncompleteException() {
        super("사용자의 추가 정보(이름, 전화번호, 주소)가 필요합니다.");

    }
}