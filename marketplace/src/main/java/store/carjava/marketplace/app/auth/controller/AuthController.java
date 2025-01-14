package store.carjava.marketplace.app.auth.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import store.carjava.marketplace.app.auth.dto.RefreshTokenRequest;
import store.carjava.marketplace.app.auth.dto.TokenResponse;
import store.carjava.marketplace.app.auth.service.AuthService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    @Value("${jwt.expiration}")
    long accessTokenExpiration;
    @Value("${jwt.refresh-token-expiration}")
    long refreshTokenExpiration;

    private final AuthService authService;

    @Hidden
    @GetMapping("/login")
    public RedirectView redirectToKeycloak() {
        log.info("Redirecting to Keycloak for authentication");
        String url = authService.getAuthorizationUrl();
        return new RedirectView(url); // Keycloak 인증 페이지로 리다이렉트
    }

    @Hidden
    @GetMapping("/login/redirect")
    @ResponseBody
    public ResponseEntity<TokenResponse> callback(HttpServletRequest request) {
        log.info("Handling callback from Keycloak");

        // Keycloak으로부터 authorization code를 가져옴
        String authorizationCode = request.getParameter("code");

        if (authorizationCode == null) {
            log.error("Authorization code not received");
            return ResponseEntity.badRequest().build();
        }

        TokenResponse tokenResponse = authService.generateJwtToken(authorizationCode);

        // Set-Cookie 헤더 추가
        ResponseCookie accessTokenCookie = createCookie("accessToken", tokenResponse.accessToken(), (int) accessTokenExpiration);
        ResponseCookie refreshTokenCookie = createCookie("refreshToken", tokenResponse.refreshToken(), (int) refreshTokenExpiration);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(tokenResponse);
    }

    @PostMapping("/login/refresh")
    public ResponseEntity<TokenResponse> refreshAccessToken(
            @RequestBody RefreshTokenRequest refreshTokenRequest) {
        log.info("Refreshing access token");

        // service로 부터 로직 수행 후, 자체 accessToken return
        return ResponseEntity.ok(authService.reIssueAccessToken(refreshTokenRequest));
    }

    private ResponseCookie createCookie(String name, String value, long maxAge) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)  // JavaScript에서 접근 불가
                .secure(true)    // HTTPS에서만 전송
                .path("/")       // 쿠키의 유효 경로
                .maxAge(maxAge)  // 쿠키의 유효 기간
                .sameSite("Strict")  // CSRF 보호를 위해 SameSite 설정
                .build();
    }

}

