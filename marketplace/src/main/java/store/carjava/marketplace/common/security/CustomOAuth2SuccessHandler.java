package store.carjava.marketplace.common.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.user.repository.UserRepository;

import java.io.IOException;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public CustomOAuth2SuccessHandler(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        OidcUser oidcUser = (OidcUser) authToken.getPrincipal();

        String email = oidcUser.getAttribute("email");
        String role = "ROLE_USER"; // 기본 역할 (필요 시 커스텀 가능)

        // DB에서 사용자 조회 또는 생성
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .email(email)
                            .role(role)
                            .build();
                    return userRepository.save(newUser);
                });

        // AccessToken과 RefreshToken 생성
        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getEmail(), user.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());

        // AccessToken과 RefreshToken을 쿠키에 저장
        addCookie(response, "accessToken", accessToken, (int) jwtTokenProvider.getAccessTokenValidity() / 1000);
        addCookie(response, "refreshToken", refreshToken, (int) jwtTokenProvider.getRefreshTokenValidity() / 1000);

        // 인증 성공 후 리다이렉션
        response.sendRedirect("/");
    }

    private void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}
