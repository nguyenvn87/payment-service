package com.uit.config;

import com.uit.dto.response.ValidClientRes;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class CommonAuthUtils {

    @Value("${payment.jwt.secret-key}")
    private String SECRET ;
    @Value("${payment.jwt.expiration}")
    private int EXPIRATION_ACCESS_TOKEN;
    @Value("${payment.vqr.username}")
    private String VALID_USERNAME ;
    @Value("${payment.vqr.password}")
    private String VALID_PASSWORD;

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

    public boolean validateJwtToken(String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
                return false;
            }
            String token = authHeader.substring(BEARER_PREFIX.length()).trim();
            log.info("=================== JwtUtils validate token =====================");
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
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

    public ValidClientRes validClientVietQr(String authHeader) {
        log.info("==================== Generating token ========================");
        ValidClientRes validClientRes = new ValidClientRes();
        validClientRes.setExpirationAccessToken(EXPIRATION_ACCESS_TOKEN);
        if (authHeader != null && authHeader.startsWith(BASIC_PREFIX)) {
            String base64Credentials = authHeader.substring(BASIC_PREFIX.length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
            final String[] values = credentials.split(":", 2);
            String username = values[0];
            String password = values[1];
            validClientRes.setUsername(username);

            log.info("==================== VALID_USERNAME  ======================== : "+ VALID_USERNAME);
            log.info("==================== VALID_PASSWORD  ======================== : "+ VALID_PASSWORD);
            log.info("==================== username  ======================== : " + username);
            log.info("==================== password  ======================== : " + password);

            if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
                log.info("==================== valid successful ========================");
                validClientRes.setValid(true);
            } else {
                log.info("==================== Invalid username or password ========================");
                validClientRes.setValid(false);
            }
        } else {
            log.info("==================== Header not start with basic ========================");
            validClientRes.setValid(false);
        }
        return validClientRes;
    }

    public String basicAuth(String username, String password) {
        String auth = username + ":" + password;
        String encoded = Base64.getEncoder().encodeToString(auth.getBytes());
        return "Basic " + encoded;
    }
}
