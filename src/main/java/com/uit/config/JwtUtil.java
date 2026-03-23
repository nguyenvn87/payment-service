package com.uit.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
public class JwtUtil {

    // secret key (nên để trong config)
    private static final String SECRET = "my-secret-key-my-secret-key-my-secret-key";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // generate token
    public static String generateToken(String username) {
        log.info("=================== JwtUtils init token =====================");
        long now = System.currentTimeMillis();
        long expirationTime = 1000 * 60 ; // 1 phút
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationTime))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean validateJwtToken(String authToken) {
        try {
            log.info("=================== JwtUtils validate token =====================");
            Jwts.parserBuilder()
                    .setSigningKey(KEY)
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
}
