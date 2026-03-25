package com.uit.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    @Value("${payment.jwt.secret-key}")
    private String SECRET ;
    @Value("${payment.jwt.expiration}")
    private int EXPIRATION_ACCESS_TOKEN;

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String BASIC_PREFIX = "Basic ";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // generate token
    public String generateToken(String username) {
        log.info("=================== JwtUtils init token =====================");
        long now = System.currentTimeMillis();
        long expirationTime = EXPIRATION_ACCESS_TOKEN ; // 1 phút
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            log.info("=================== JwtUtils validate token =====================");
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String basicAuth(String username, String password) {
        String auth = username + ":" + password;
        String encoded = Base64.getEncoder().encodeToString(auth.getBytes());
        return "Basic " + encoded;
    }
}
