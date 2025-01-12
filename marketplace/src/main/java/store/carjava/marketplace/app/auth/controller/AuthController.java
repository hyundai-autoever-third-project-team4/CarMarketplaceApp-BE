package store.carjava.marketplace.app.auth.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import store.carjava.marketplace.app.auth.dto.TokenResponse;
import store.carjava.marketplace.app.auth.service.AuthService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

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

        // service로 부터 로직 수행 후, 자체 accessToken, refreshToken 받기
        return ResponseEntity.ok(authService.generateJwtToken(authorizationCode));
    }
}

