package store.carjava.marketplace.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

    private static final List<String> publicPaths = List.of(
            // [Swagger] 모두 허용
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/api-docs/**",

            // [Thymleaf] 모두 허용
            "/resources/**",

            // [Oauth 2.0] 로그인 경로 모두 허용
            "/login-page",
            "/login/**",
            "/mock-login/**"

    );

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // CSRF 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        // 세션 관리 설정
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // CORS 설정 추가
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http
                .authorizeHttpRequests(authorize -> authorize
                        // [Common] 공통 공개 경로
                        .requestMatchers(publicPaths.toArray(String[]::new)).permitAll()

                        // [Marketplace Car]
                        .requestMatchers(HttpMethod.GET, "/cars/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/car/recommend").permitAll()
                        .requestMatchers("/car/**").authenticated()

                        // [Reservation]
                        .requestMatchers(HttpMethod.GET, "/reservations/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/reservation/**").authenticated()

                        // [My page]
                        .requestMatchers("/mypage/**").authenticated()

                        // [Review]
                        .requestMatchers("/reviews/**").permitAll()
                        .requestMatchers("/review/**").authenticated()

                        // [Test Driver Center]
                        .requestMatchers("/test-driver-centers/**").permitAll()

                        // [ADMIN]
                        .requestMatchers("/admin/**").hasRole("ADMIN") // ROLE_ADMIN만 허용
                        //.requestMatchers("/admin/**").permitAll()

                        .requestMatchers("/payment/**").permitAll()
                        // socket
                        .requestMatchers("/ws/**").permitAll()


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
        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:5173",
                "http://127.0.0.1:5500",
                "http://localhost:8081",
                "https://chajava.store"
        ));
        // 허용할 HTTP 메서드 설정
        configuration.setAllowedMethods(
                Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // 허용할 헤더 설정
        configuration.setAllowedHeaders(
                Arrays.asList("Authorization", "Content-Type", "Accept", "Origin"));

        //인증 정보 허용
        configuration.setAllowCredentials(true);

        // 클라이언트에서 접근 가능한 헤더 설정
        configuration.setExposedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
