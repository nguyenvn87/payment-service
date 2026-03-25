package com.uit.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uit.common.exceptions.PaymentSuccess;
import com.uit.config.JwtUtil;
import com.uit.dto.request.TransactionCallback;
import com.uit.dto.request.TransactionResponseObject;
import com.uit.dto.response.ErrorResponse;
import com.uit.dto.response.SuccessResponse;
import com.uit.dto.response.TokenResponse;
import com.uit.payment.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("payment/v1")
public class PaymentController {

    @Value("${payment.vqr.username}")
    private String VALID_USERNAME ;
    @Value("${payment.vqr.password}")
    private String VALID_PASSWORD;
    @Value("${payment.jwt.expiration}")
    private int EXPIRATION_ACCESS_TOKEN;

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;
    private final PaymentService paymentService;


    public PaymentController(JwtUtil jwtUtil, PaymentService paymentService) {
        this.jwtUtil = jwtUtil;
        this.paymentService = paymentService;
    }

    @PostMapping("/api/token_generate")
    public ResponseEntity<?> generateToken(@RequestHeader("Authorization") String authHeader) {
        log.info("==================== Generating token ========================");
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            String base64Credentials = authHeader.substring("Basic ".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
            final String[] values = credentials.split(":", 2);
            String username = values[0];
            String password = values[1];

            log.info("==================== VALID_USERNAME  ======================== : "+ VALID_USERNAME);
            log.info("==================== VALID_PASSWORD  ======================== : "+ VALID_PASSWORD);
            log.info("==================== username  ======================== : " + username);
            log.info("==================== password  ======================== : " + password);

            if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
                String token = jwtUtil.generateToken(username); // Ở đây bạn cần tạo JWT token thực sự, ví dụ với jjwt.
                log.info("==================== valid successful ========================");
                return ResponseEntity.ok(new TokenResponse(token, "Bearer", EXPIRATION_ACCESS_TOKEN));
            } else {
                log.info("==================== Invalid username or password ========================");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } else {
            log.info("==================== Header not start with basic ========================");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authorization header is missing or invalid");
        }
    }

    @PostMapping("/bank/api/transaction-sync")
    public ResponseEntity<?> transactionSync(@RequestBody TransactionCallback transactionCallback,
                                                  HttpServletRequest request) {
        // Lấy token từ header Authorization
        log.info("==================== Transaction sync ========================");
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return new ResponseEntity<>(new ErrorResponse(true, "INVALID_AUTH_HEADER",
                    "Authorization header is missing or invalid", null), HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(BEARER_PREFIX.length()).trim();

        log.info("==================== validate with token ======================== : " + token);
        // Xác thực token
        if (!jwtUtil.validateJwtToken(token)) {
            log.info("==================== validate token fail ========================");
            return new ResponseEntity<>(new ErrorResponse(true, "INVALID_TOKEN",
                    "Invalid or expired token", null), HttpStatus.UNAUTHORIZED);
        }
        log.info("==================== validate token successful 1 ========================");

        try {
            // Xử lý nghiệp vụ, sinh mã refTransactionId (Giả sử tạo một mã ngẫu nhiên)

            log.info("sync transaction info : " + transactionCallback.getTransactionid());
            log.info("sync transaction info : " + transactionCallback.getTransactiontime());
            log.info("sync transaction info : " + transactionCallback.getReferencenumber());
            log.info("sync transaction info : " + transactionCallback.getAmount());
            log.info("sync transaction info : " + transactionCallback.getContent());
            log.info("sync transaction info : " + transactionCallback.getBankaccount());
            log.info("sync transaction info : " + transactionCallback.getOrderId());
            log.info("sync transaction info : " + transactionCallback.getSign());
            log.info("sync transaction info : " + transactionCallback.getTerminalCode());
            log.info("sync transaction info : " + transactionCallback.getUrlLink());
            log.info("sync transaction info : " + transactionCallback.getServiceCode());
            log.info("sync transaction info : " + transactionCallback.getSubTerminalCode());

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(transactionCallback);
            log.info(json);

            paymentService.updateInformationPayment(transactionCallback);
            // Trả về response 200 OK với thông tin giao dịch
            TransactionResponseObject transactionResponse = new TransactionResponseObject(transactionCallback.getTransactionid());

            log.info("==================== validate token successful 2 ========================");
            return ResponseEntity.ok(new SuccessResponse(false, null,
                    "Transaction processed successfully", transactionResponse));
        } catch (Exception ex) {
            // Trả về lỗi trong trường hợp có exception
            log.info("==================== TRANSACTION FAILED ========================");
            return new ResponseEntity<>(new ErrorResponse(true, "TRANSACTION_FAILED", ex.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

}
