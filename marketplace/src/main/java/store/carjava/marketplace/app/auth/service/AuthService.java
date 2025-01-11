package store.carjava.marketplace.app.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import store.carjava.marketplace.app.auth.dto.TokenRequest;
import store.carjava.marketplace.app.auth.dto.TokenResponse;
import store.carjava.marketplace.app.user.entity.User;
import store.carjava.marketplace.app.user.repository.UserRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final UserRepository userRepository;

    public TokenResponse requestAccessToken(String tokenUri, TokenRequest tokenRequest,
            String clientId, String clientSecret, String redirectUri) {
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

    public Map<String, Object> requestUserInfo(String userInfoUri, String accessToken) {
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

    public void saveOrUpdateUser(Map<String, Object> userInfo) {
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
