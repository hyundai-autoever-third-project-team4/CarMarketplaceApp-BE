package store.carjava.marketplace.common.util.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.user.exception.UserIdNotFoundException;
import store.carjava.marketplace.app.user.exception.UserProfileIncompleteException;
import store.carjava.marketplace.app.user.repository.UserRepository;
import store.carjava.marketplace.common.security.UserNotAuthenticatedException;

@Component
@RequiredArgsConstructor
public class UserResolver {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        // 1) Security Context에서 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 2) 인증되지 않은 사용자인 경우 예외 처리
        if (!authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new UserNotAuthenticatedException(); // 예외 발생
        }

        // 3) principal에서 User 객체 추출
        Object principal = authentication.getPrincipal();

        // 4) principal에서 userid 추축
        Long userId = Long.valueOf(((UserDetails) principal).getUsername()); // UserDetails의 username = userId

        // 5) 사용자 정보 조회
        User user = userRepository.findById(userId)
                .orElseThrow(UserIdNotFoundException::new);

        // 6) 프로필 정보 완성 여부 확인
        if (!user.isProfileComplete()) {
            throw new UserProfileIncompleteException();
        }

        return user;
    }


    // 유저 정보 업데이트를 위한. 추가 정보 검증없이 유저정보 반환
    public User getCurrentUserWithoutProfileCheck() {
        // 1) Security Context에서 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 2) 인증되지 않은 사용자인 경우 예외 처리
        if (!authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new UserNotAuthenticatedException();
        }

        // 3) principal에서 User 객체 추출
        Object principal = authentication.getPrincipal();

        // 4) principal에서 userid 추출
        Long userId = Long.valueOf(((UserDetails) principal).getUsername());

        // 5) 사용자 정보 조회
        return userRepository.findById(userId)
                .orElseThrow(UserIdNotFoundException::new);
    }
}
