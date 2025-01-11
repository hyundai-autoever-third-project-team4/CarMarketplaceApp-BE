package store.carjava.marketplace.common.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request); // 요청에서 토큰 추출

        if (token != null) {
            try {
                // JWT 디코딩 및 유효성 검사
                Jwt jwt = jwtDecoder.decode(token);

                // JWT에서 사용자 정보 추출
                String email = jwt.getClaimAsString("email");
                Map<String, Object> realmAccess = jwt.getClaim("realm_access");
                List<String> roles = (List<String>) realmAccess.get("roles");

                // ROLE_로 시작하는 값을 추출하거나 기본값 ROLE_USER 설정
                String role = roles.stream()
                        .filter(r -> r.startsWith("ROLE_"))
                        .findFirst()
                        .orElse("ROLE_USER");

                // UserDetails 및 Authentication 객체 생성
                UserDetails userDetails = new User(
                        email,
                        "", // JWT 기반 인증에서는 비밀번호가 필요하지 않음
                        List.of(new SimpleGrantedAuthority(role))
                );

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // SecurityContext에 인증 정보 설정
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JwtException e) {
                // JWT 유효성 검사 실패 처리
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired token: " + e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response); // 다음 필터로 요청 전달
    }


    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization"); // Authorization 헤더에서 토큰 추출
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Bearer 접두사 제거 후 반환
        }
        return null; // 토큰이 없거나 형식이 잘못된 경우 null 반환
    }
}
