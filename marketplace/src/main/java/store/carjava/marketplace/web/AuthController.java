package store.carjava.marketplace.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final String clientId = "key-cloak-malak";
    private final String clientSecret = "Z35EFO8DwEJrzhgfWS18aoDKg2FwFbjw";
    private final String redirectUri = "http://localhost:8081/login/callback";
    private final String authorizationUri = "https://keycloakmalak.site/realms/key-cloak-malak-realm/protocol/openid-connect/auth";
    private final String tokenUri = "https://keycloakmalak.site/realms/key-cloak-malak-realm/protocol/openid-connect/token";

    private final RestTemplate restTemplate = new RestTemplate();
    private final JwtDecoder jwtDecoder;

    private final UserRepository userRepository;

    @GetMapping("/login")
    public String loginPage() {
        return "login/index";
    }

    @GetMapping("/login/redirect")
    public RedirectView redirectToKeycloak() {
        String url = authorizationUri +
                "?response_type=code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&scope=openid profile email";

        return new RedirectView(url);
    }

    @GetMapping("/login/callback")
    public String callback(HttpServletRequest request, Model model) {
        String code = request.getParameter("code");
        model.addAttribute("authorizationCode", code);
        return "login/callback";
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

        // Decode JWT and extract user information
        String accessToken = tokenResponse.getAccessToken();
        Jwt jwt = jwtDecoder.decode(accessToken);

        String email = jwt.getClaimAsString("email");

        // Extract "roles" from "realm_access"
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        List<String> roles = (List<String>) realmAccess.get("roles");

        // Extract ROLE_ values or assign default ROLE_USER
        String role = roles.stream()
                .filter(r -> r.startsWith("ROLE_"))
                .findFirst()
                .orElse("ROLE_USER");

        // 저장 또는 업데이트 로직
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .email(email)
                            .role(role)
                            .build();
                    return userRepository.save(newUser);
                });

        if (!user.getRole().equals(role)) {
            userRepository.save(user.updateRole(role));
        }

        // Create UserDetails and Authentication object
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                "",
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(tokenResponse);
    }



}
