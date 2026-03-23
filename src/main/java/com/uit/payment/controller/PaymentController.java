package com.uit.payment.controller;

import com.uit.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/vqr/api")
public class PaymentController {

    private static final String VALID_USERNAME = "customer-vietqrtest-user2468";
    // Đây là chuỗi base64 từ username:password thật của bạn.
    private static final String VALID_PASSWORD = "Y3VzdG9tZXItdmlldHFydGVzdC11c2VyMjQ2ODpZM1Z6ZEc5dFpYSXRkbWxsZEhGeWRHVnpkQzExYzJWeU1qUTJPQT09";

    private static final String SECRET_KEY = "your-256-bit-secret"; // Secret key để kiểm tra JWT
    private static final String BEARER_PREFIX = "Bearer ";

    @PostMapping("/token_generate")
    public ResponseEntity<?> generateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            String base64Credentials = authHeader.substring("Basic ".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
            final String[] values = credentials.split(":", 2);
            String username = values[0];
            String password = values[1];

            // Kiểm tra tính hợp lệ của username và password
            if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
                // Nếu hợp lệ, tạo JWT token
                String token = "your-generated-jwt-token"; // Ở đây bạn cần tạo JWT token thực sự, ví dụ với jjwt.

                return ResponseEntity.ok(new TokenResponse(token, "Bearer", 300));
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
        if (!validateToken(token)) {
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

    // Phương thức để xác thực token JWT
    private boolean validateToken(String token) {
        // Đây là phương pháp giả sử validate token với SECRET_KEY
        // Bạn có thể tích hợp JWT library như `io.jsonwebtoken` để validate
        try {
            // Giả sử giải mã token với SECRET_KEY
            byte[] secretKeyBytes = SECRET_KEY.getBytes();
            String decodedToken = new String(Base64.getDecoder().decode(token.getBytes()));

            // Kiểm tra token hợp lệ (thực tế nên sử dụng JWT library như jjwt)
            return decodedToken.contains(SECRET_KEY);
        } catch (Exception e) {
            return false;
        }
    }
}
