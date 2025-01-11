package store.carjava.marketplace.app.auth.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import store.carjava.marketplace.app.auth.dto.TokenRequest;
import store.carjava.marketplace.app.auth.dto.TokenResponse;
import store.carjava.marketplace.app.auth.service.AuthService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.keycloak.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.keycloak.authorization-uri}")
    private String authorizationUri;

    @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
    private String tokenUri;

    @Value("${spring.security.oauth2.client.provider.keycloak.user-info-uri}")
    private String userInfoUri;

    private final AuthService authService; // AuthService 주입

    @Hidden
    @GetMapping("/login")
    public RedirectView redirectToKeycloak() {
        log.info("Redirecting to Keycloak for authentication");
        String url = authorizationUri +
                "?response_type=code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&scope=openid profile email";

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

        // Access Token 요청
        TokenRequest tokenRequest = new TokenRequest(authorizationCode);

        TokenResponse tokenResponse = authService.requestAccessToken(
                tokenUri, tokenRequest, clientId, clientSecret, redirectUri
        );

        // 사용자 정보 요청 및 저장
        Map<String, Object> userInfo = authService.requestUserInfo(userInfoUri, tokenResponse.accessToken());
        authService.saveOrUpdateUser(userInfo);

        log.info("Returning tokens to the frontend");
        return ResponseEntity.ok(tokenResponse);
    }
}
