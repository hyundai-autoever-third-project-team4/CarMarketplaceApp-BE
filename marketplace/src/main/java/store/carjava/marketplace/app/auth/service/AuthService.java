package store.carjava.marketplace.app.auth.service;

import io.jsonwebtoken.Claims;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import store.carjava.marketplace.app.auth.dto.RefreshTokenRequest;
import store.carjava.marketplace.app.auth.dto.TokenRequest;
import store.carjava.marketplace.app.auth.dto.TokenResponse;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.user.repository.UserRepository;

import java.util.Map;
import store.carjava.marketplace.common.jwt.JwtTokenProvider;
import store.carjava.marketplace.common.jwt.JwtTokenVerifier;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenVerifier jwtTokenVerifier;

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

    public String getAuthorizationUrl() {
        return authorizationUri +
                "?response_type=code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&scope=openid profile email";
    }

    public TokenResponse generateJwtToken(String authorizationCode) {
        // 1) Authorization Code를 통해 key cloak에 accessToken, refreshToken 요청 생성
        TokenRequest keycloakTokenRequest = TokenRequest.of(authorizationCode);

        // 2) 해당 요청을 keycloak에 보내, accessToken, refreshToken response 생성
        TokenResponse keycloakTokenResponse = requestAccessToken(keycloakTokenRequest);

        // 3) response에 들어있는 accessToken을 통해, keycloak에 로그인 한 유저의 user_info 조회
        Map<String, Object> userInfo = requestUserInfo(keycloakTokenResponse.accessToken());

        // 4) 해당 user info를 바탕으로 db에 유저 생성 및 update
        User user = saveOrUpdateUser(userInfo);

        // 5) user 정보를 바탕으로 chajava만의 jwt 생성
        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());

        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse reIssueAccessToken(RefreshTokenRequest refreshTokenRequest) {

        String refreshToken = refreshTokenRequest.refreshToken();

        // 1) refreshToken verify
        Claims claims = jwtTokenVerifier.verifyToken(refreshToken);

        // 2) claim에서 sub (user id) 추출
        Long userId = Long.valueOf(claims.getSubject());

        // 3) userId로 user 조회
        User user = userRepository.findById(userId)
                .orElseThrow();

        String accessToken = jwtTokenProvider.generateAccessToken(user);

        return new TokenResponse(accessToken, refreshToken);
    }

    private TokenResponse requestAccessToken(TokenRequest tokenRequest) {
        log.info("Calling Keycloak token endpoint");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", tokenRequest.authorizationCode());
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
        return response.getBody();
    }

    private User saveOrUpdateUser(Map<String, Object> userInfo) {
        log.info("Saving or updating user in the database");

        String email = (String) userInfo.get("email");
        String preferredUsername = (String) userInfo.get("preferred_username");

        // groups에서 ROLE_로 시작하는 역할 추출
        List<String> groups = (List<String>) userInfo.get("groups");
        String role = groups.stream()
                .filter(group -> group.startsWith("ROLE_")) // ROLE_로 시작하는 역할 필터링
                .findFirst()
                .orElse("ROLE_USER"); // 없으면 기본 ROLE_USER 설정

        // 기존 사용자 조회 또는 새 사용자 생성
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    log.info("Creating a new user with email: {}", email);
                    User newUser = User.builder()
                            .email(email)
                            .name(preferredUsername)
                            .role(role) // 추출한 역할 설정
                            .build();
                    return userRepository.save(newUser);
                });

        // 역할이 변경된 경우 업데이트
        if (!user.getRole().equals(role)) {
            log.info("Updating role for user: {} from {} to {}", email, user.getRole(), role);
            user = user.updateRole(role);
            userRepository.save(user);
        }

        log.info("User saved or updated: {}", user);
        return user;
    }

}
