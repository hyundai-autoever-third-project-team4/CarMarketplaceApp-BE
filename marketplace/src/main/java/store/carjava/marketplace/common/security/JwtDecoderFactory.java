package store.carjava.marketplace.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtDecoderFactory {

    private static final String KEYCLOAK_JWK_SET_URI = "https://keycloakmalak.site/realms/key-cloak-malak-realm/protocol/openid-connect/certs";

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(KEYCLOAK_JWK_SET_URI).build();
    }
}
