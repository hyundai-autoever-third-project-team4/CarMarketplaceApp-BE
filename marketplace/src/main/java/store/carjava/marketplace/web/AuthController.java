package store.carjava.marketplace.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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

    private final RestTemplate restTemplate = new RestTemplate(); // RestTemplate 객체
    private final JwtDecoder jwtDecoder; // JWT 디코더
    private final UserRepository userRepository; // 사용자 저장소

    @GetMapping("/login")
    public String loginPage() {
        return "login/index"; // 로그인 페이지 반환
    }

    @GetMapping("/login/redirect")
    public RedirectView redirectToKeycloak() {
        String url = authorizationUri +
                "?response_type=code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&scope=openid profile email";

        return new RedirectView(url); // 리다이렉트 URL 반환
    }

    @GetMapping("/login/callback")
    public String callback(HttpServletRequest request, Model model) {
        String code = request.getParameter("code");
        model.addAttribute("authorizationCode", code);
        return "login/callback"; // Callback 페이지 반환
    }

    @PostMapping("/login/auth/token")
    @ResponseBody
    public ResponseEntity<TokenResponse> getToken(@RequestBody TokenRequest tokenRequest) {
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

        TokenResponse tokenResponse = response.getBody();

        // AccessToken 디코드 및 사용자 정보 추출
        String accessToken = tokenResponse.getAccessToken();
        Jwt jwt = jwtDecoder.decode(accessToken);

        String email = jwt.getClaimAsString("email");

        // "realm_access"에서 역할 추출
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        List<String> roles = (List<String>) realmAccess.get("roles");

        // ROLE_로 시작하는 값을 추출하거나 기본 ROLE_USER 설정
        String role = roles.stream()
                .filter(r -> r.startsWith("ROLE_"))
                .findFirst()
                .orElse("ROLE_USER");

        // 사용자 저장 또는 업데이트 로직
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .email(email)
                            .role(role)
                            .build();
                    return userRepository.save(newUser);
                });

        if (!user.getRole().equals(role)) {
            userRepository.save(user.updateRole(role)); // 사용자 역할 업데이트
        }

        return ResponseEntity.ok(tokenResponse); // 토큰 응답 반환
    }
}
