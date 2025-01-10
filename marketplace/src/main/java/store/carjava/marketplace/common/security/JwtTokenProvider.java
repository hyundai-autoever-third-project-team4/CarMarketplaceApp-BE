package store.carjava.marketplace.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    // AccessToken 만료 시간 반환
    @Getter
    @Value("${jwt.expiration}")
    private long accessTokenValidity;

    // RefreshToken 만료 시간 반환
    @Getter
    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenValidity;

    // AccessToken 생성
    public String createAccessToken(Long userId, String email, String role) {
        return createToken(userId, email, role, accessTokenValidity);
    }

    // RefreshToken 생성
    public String createRefreshToken(Long userId) {
        return createToken(userId, null, null, refreshTokenValidity);
    }

    // 공통 토큰 생성 메서드
    private String createToken(Long userId, String email, String role, long validity) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        if (email != null) claims.put("email", email);
        if (role != null) claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // JWT에서 클레임 추출
    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
