package store.carjava.marketplace.common.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.user.repository.UserRepository;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.io.IOException;
import java.util.List;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public CustomOAuth2SuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 인증된 사용자 정보 가져오기
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        OidcUser oidcUser = (OidcUser) authToken.getPrincipal();

        // 사용자 정보 추출
        String email = oidcUser.getAttribute("email"); // 이메일
        List<String> groups = oidcUser.getAttribute("groups"); // 역할 정보

        // 역할 필터링: ROLE_로 시작하는 값만 추출
        String role = groups.stream()
                .filter(group -> group.startsWith("ROLE_"))
                .findFirst()
                .orElse("ROLE_USER"); // 기본 역할 설정

        // 기존 사용자 조회
        User existingUser = userRepository.findByEmail(email).orElse(null);

        if (existingUser == null) {
            // 신규 사용자 생성 및 저장
            User newUser = User.builder()
                    .email(email)
                    .role(role)
                    .build();
            userRepository.save(newUser);
        } else if (!existingUser.getRole().equals(role)) {
            // 역할이 변경되었을 경우 새로운 객체 생성 및 저장
            User updatedUser = existingUser.updateRole(role);
            userRepository.save(updatedUser);
        }

        // 인증 성공 후 리다이렉션
        response.sendRedirect("/");
    }
}

