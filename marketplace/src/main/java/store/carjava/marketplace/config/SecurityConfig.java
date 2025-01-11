package store.carjava.marketplace.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import store.carjava.marketplace.common.security.JwtAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // CSRF 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        // 세션 관리 설정
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // CORS 설정 추가
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        // 인증 및 권한 설정
        List<String> publicPaths = List.of(
                "/swagger-ui/**",
                "/api-docs/**",
                "/resources/**",
                "/login/**"
        );

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(publicPaths.toArray(String[]::new)).permitAll() // 공개 경로
                        .requestMatchers("/admin/**").hasRole("ADMIN") // ROLE_ADMIN만 허용
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                // 커스텀 JWT 필터 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

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
        configuration.setAllowedMethods(
                Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // 허용할 헤더 설정
        configuration.setAllowedHeaders(
                Arrays.asList("Authorization", "Content-Type", "Accept", "Origin"));

        // 클라이언트에서 접근 가능한 헤더 설정
        configuration.setExposedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
