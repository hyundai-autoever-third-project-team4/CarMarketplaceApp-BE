package store.carjava.marketplace.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AuthController {

    private final String clientId = "key-cloak-malak";
    private final String redirectUri = "http://localhost:8081/login/callback";
    private final String authorizationUri = "https://keycloakmalak.site/realms/key-cloak-malak-realm/protocol/openid-connect/auth";

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
}
