package com.uit.payment.controller;

import com.uit.config.JwtUtil;
import com.uit.dto.request.TransactionCallback;
import com.uit.dto.request.TransactionResponseObject;
import com.uit.dto.response.ErrorResponse;
import com.uit.dto.response.SuccessResponse;
import com.uit.dto.response.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("api/v1/vqr")
public class PaymentController {

    @Value("${security.vqr.username}")
    private String VALID_USERNAME ;
    @Value("${security.vqr.password}")
    private String VALID_PASSWORD;
    @Value("${security.jwt.expiration}")
    private int EXPIRATION_ACCESS_TOKEN;

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;

    public PaymentController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/token_generate")
    public ResponseEntity<?> generateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            String base64Credentials = authHeader.substring("Basic ".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
            final String[] values = credentials.split(":", 2);
            String username = values[0];
            String password = values[1];

            if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
                String token = jwtUtil.generateToken(username); // Ở đây bạn cần tạo JWT token thực sự, ví dụ với jjwt.

                return ResponseEntity.ok(new TokenResponse(token, "Bearer", EXPIRATION_ACCESS_TOKEN));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authorization header is missing or invalid");
        }
    }

    @PostMapping("/transaction-sync")
    public ResponseEntity<Object> transactionSync(@RequestBody TransactionCallback transactionCallback,
                                                  HttpServletRequest request) {
        // Lấy token từ header Authorization
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return new ResponseEntity<>(new ErrorResponse(true, "INVALID_AUTH_HEADER",
                    "Authorization header is missing or invalid", null), HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(BEARER_PREFIX.length()).trim();

        // Xác thực token
        if (jwtUtil.validateJwtToken(token)) {
            return new ResponseEntity<>(new ErrorResponse(true, "INVALID_TOKEN",
                    "Invalid or expired token", null), HttpStatus.UNAUTHORIZED);
        }

        try {
            // Xử lý nghiệp vụ, sinh mã refTransactionId (Giả sử tạo một mã ngẫu nhiên)
            String refTransactionId = "GeneratedRefTransactionId"; // Sinh ID của giao dịch

            // Trả về response 200 OK với thông tin giao dịch
            TransactionResponseObject transactionResponse = new TransactionResponseObject(refTransactionId);
            return ResponseEntity.ok(new SuccessResponse(false, null,
                    "Transaction processed successfully", transactionResponse));
        } catch (Exception ex) {
            // Trả về lỗi trong trường hợp có exception
            return new ResponseEntity<>(new ErrorResponse(true, "TRANSACTION_FAILED", ex.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

}
