package store.carjava.marketplace.common.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import store.carjava.marketplace.app.user.entity.User;

@Component
public class JwtTokenProvider {

    private final Key signingKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    /**
     * Access Token 생성
     */
    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId())) // sub에 userId 설정
                .setIssuer("https://chajava.store")       // iss 설정
                .setAudience("account")                     // aud 설정
                .setIssuedAt(new Date())                 // iat 설정
                .setExpiration(
                        new Date(System.currentTimeMillis() + accessTokenExpiration)) // exp 설정
                .claim("role", user.getRole())           // role 추가 (커스텀 클레임)
                .claim("typ", "Bearer")                  // typ 추가
                .signWith(signingKey, SignatureAlgorithm.HS256) // 서명
                .compact();
    }


    /**
     * Refresh Token 생성
     */
    public String generateRefreshToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId)) // sub에 userId 설정
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
