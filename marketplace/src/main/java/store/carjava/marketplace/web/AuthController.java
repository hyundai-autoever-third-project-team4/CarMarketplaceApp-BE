package store.carjava.marketplace.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

@Controller
public class AuthController {

    private final String clientId = "key-cloak-malak";
    private final String clientSecret = "Z35EFO8DwEJrzhgfWS18aoDKg2FwFbjw";
    private final String redirectUri = "http://localhost:8081/login/callback";
    private final String authorizationUri = "https://keycloakmalak.site/realms/key-cloak-malak-realm/protocol/openid-connect/auth";
    private final String tokenUri = "https://keycloakmalak.site/realms/key-cloak-malak-realm/protocol/openid-connect/token";

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Display the login page
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login/index";
    }

    /**
     * Redirect to Keycloak's Authorization Endpoint
     */
    @GetMapping("/login/redirect")
    public RedirectView redirectToKeycloak() {
        String url = authorizationUri +
                "?response_type=code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&scope=openid profile email";

        return new RedirectView(url);
    }

    /**
     * Handle Authorization Code Callback and display the code
     */
    @GetMapping("/login/callback")
    public String callback(HttpServletRequest request, Model model) {
        // Get the authorization code from the request parameter
        String code = request.getParameter("code");

        System.out.println(code);

        // Add the code to the model to display in the view
        model.addAttribute("authorizationCode", code);

        // Render the view to display the code
        return "login/callback";
    }

    /**
     * Handle authorization-code and return accessToken and refreshToken
     */
    @PostMapping("/login/auth/token")
    @ResponseBody
    public ResponseEntity<TokenResponse> getToken(@RequestBody TokenRequest tokenRequest) {
        // HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Request body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", tokenRequest.getAuthorizationCode());
        body.add("redirect_uri", redirectUri);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        // Send POST request to Keycloak token endpoint
        ResponseEntity<TokenResponse> response = restTemplate.exchange(
                tokenUri,
                HttpMethod.POST,
                requestEntity,
                TokenResponse.class
        );

        return ResponseEntity.ok(response.getBody());
    }
}
