package store.carjava.marketplace.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.user.repository.UserRepository;

import java.util.List;
import java.util.Map;

@Controller
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

    private final RestTemplate restTemplate = new RestTemplate(); // RestTemplate 객체
    private final JwtDecoder jwtDecoder; // JWT 디코더
    private final UserRepository userRepository; // 사용자 저장소

    @GetMapping("/login")
    public String loginPage() {
        log.info("Navigating to login page");
        return "login/index"; // 로그인 페이지 반환
    }

    @GetMapping("/login/redirect")
    public RedirectView redirectToKeycloak() {
        log.info("Redirecting to Keycloak for authentication");
        String url = authorizationUri +
                "?response_type=code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&scope=openid profile email";

        return new RedirectView(url); // 리다이렉트 URL 반환
    }

    @GetMapping("/login/callback")
    public String callback(HttpServletRequest request, Model model) {
        log.info("Handling callback from Keycloak");
        String code = request.getParameter("code");
        model.addAttribute("authorizationCode", code);
        return "login/callback"; // Callback 페이지 반환
    }

    @PostMapping("/login/auth/token")
    @ResponseBody
    public ResponseEntity<TokenResponse> getToken(@RequestBody TokenRequest tokenRequest) {
        log.info("Requesting access token from Keycloak");
        TokenResponse tokenResponse = requestAccessToken(tokenRequest);
        String accessToken = tokenResponse.getAccessToken();

        log.info("Requesting user info from Keycloak");
        Map<String, Object> userInfo = requestUserInfo(accessToken);

        log.info("Saving or updating user in the database");
        saveOrUpdateUser(userInfo);

        return ResponseEntity.ok(tokenResponse); // 토큰 응답 반환
    }

    private TokenResponse requestAccessToken(TokenRequest tokenRequest) {
        log.info("Calling Keycloak token endpoint");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", tokenRequest.getAuthorizationCode());
        body.add("redirect_uri", redirectUri);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<TokenResponse> response = restTemplate.exchange(
                tokenUri,
                HttpMethod.POST,
                requestEntity,
                TokenResponse.class
        );

        log.info("Access token received successfully");
        return response.getBody();
    }

    private Map<String, Object> requestUserInfo(String accessToken) {
        log.info("Calling Keycloak user info endpoint");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                userInfoUri,
                HttpMethod.GET,
                requestEntity,
                Map.class
        );

        log.info("User info received successfully");
        Map<String, Object> userInfo = response.getBody();

        // Log all user info fields
        if (userInfo != null) {
            userInfo.forEach((key, value) -> log.info("User info field - {}: {}", key, value));
        } else {
            log.warn("No user info received");
        }

        return userInfo;
    }


    private void saveOrUpdateUser(Map<String, Object> userInfo) {
        log.info("Saving or updating user in the database");
        String email = (String) userInfo.get("email");
        String preferredUsername = (String) userInfo.get("preferred_username");

        userRepository.findByEmail(email)
                .orElseGet(() -> {
                    log.info("Creating a new user with email: {}", email);
                    User newUser = User.builder()
                            .email(email)
                            .name(preferredUsername)
                            .role("ROLE_USER") // 기본 역할 설정
                            .build();
                    return userRepository.save(newUser);
                });
    }
}
