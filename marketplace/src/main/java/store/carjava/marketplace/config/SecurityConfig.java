package store.carjava.marketplace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import store.carjava.marketplace.common.security.CustomOAuth2SuccessHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfig {
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    public SecurityConfig(CustomOAuth2SuccessHandler customOAuth2SuccessHandler) {
        this.customOAuth2SuccessHandler = customOAuth2SuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // CSRF 비활성화 (모든 경로)
        http.csrf(AbstractHttpConfigurer::disable);

        // 세션 관리 설정
        http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));

        // CORS 설정 추가
        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));

        // 인증 없이 허용할 경로 리스트
        List<String> publicPaths = List.of(
                "/",
                "/swagger-ui/**",
                "/api-docs/**",
                "/resources/**",
                "/login/**"
        );

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(publicPaths.toArray(String[]::new)).permitAll() // 공개 경로
                        .requestMatchers("/admin/**").hasRole("ADMIN") // /admin 경로는 ROLE_ADMIN만 접근 가능
                        .anyRequest().authenticated() // 나머지 경로는 인증된 사용자만 접근 가능
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 허용할 출처 설정
        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:8081",
                "https://chajava.store"
        ));

        // 허용할 HTTP 메서드 설정
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // 쿠키와 자격 증명 사용을 허용
        configuration.setAllowCredentials(true);

        // 허용할 헤더 설정
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "Origin"));

        // 클라이언트에서 접근 가능한 헤더 설정
        configuration.setExposedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
