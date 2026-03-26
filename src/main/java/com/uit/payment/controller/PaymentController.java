package com.uit.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uit.common.JsonUtil;
import com.uit.common.exceptions.PaymentException;
import com.uit.config.CommonAuthUtils;
import com.uit.dto.request.TransactionCallback;
import com.uit.dto.request.TransactionResponseObject;
import com.uit.dto.response.ErrorResponse;
import com.uit.dto.response.SuccessResponse;
import com.uit.dto.response.TokenResponse;
import com.uit.dto.response.ValidClientRes;
import com.uit.payment.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("payment/v1")
public class PaymentController {

    private final CommonAuthUtils commonAuthUtils;
    private final PaymentService paymentService;

    public PaymentController(CommonAuthUtils commonAuthUtils, PaymentService paymentService ) {
        this.commonAuthUtils = commonAuthUtils;
        this.paymentService = paymentService;
    }

    @PostMapping("/api/token_generate")
    public ResponseEntity<?> generateToken(@RequestHeader("Authorization") String authHeader) {
        log.info("==================== Generating token ========================");

        ValidClientRes validClientRes = commonAuthUtils.validClientVietQr(authHeader);
        if (validClientRes.isValid()) {
            String token = commonAuthUtils.generateToken(validClientRes.getUsername());
            log.info("==================== valid successful ========================");
            return ResponseEntity.ok(new TokenResponse(token, "Bearer", validClientRes.getExpirationAccessToken()));
        } else {
            log.info("==================== Header not start with basic ========================");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authorization header is missing or invalid");
        }
    }

    @PostMapping("/bank/api/transaction-sync")
    public ResponseEntity<?> transactionSync(@RequestBody TransactionCallback transactionCallback,
                                                  HttpServletRequest request) {
        log.info("==================== Transaction sync ========================");
        if (!commonAuthUtils.validateJwtToken(request.getHeader("Authorization"))) {
            return new ResponseEntity<>(new ErrorResponse(true, "INVALID_TOKEN",
                    "Invalid or expired token", null), HttpStatus.UNAUTHORIZED);
        }
        try {
            log.info(JsonUtil.toJson(transactionCallback));
            paymentService.updateInformationPayment(transactionCallback);
            TransactionResponseObject transactionResponse = new TransactionResponseObject(transactionCallback.getTransactionid());
            return ResponseEntity.ok(new SuccessResponse(false, null,
                    "Transaction processed successfully", transactionResponse));
        } catch (PaymentException ex) {
            return new ResponseEntity<>(new ErrorResponse(true, "TRANSACTION_FAILED", ex.getErrorMessage(), null), ex.getErrorStatusCode());
        } catch (Exception ex) {
            return new ResponseEntity<>(new ErrorResponse(true, "TRANSACTION_FAILED", ex.getMessage(), null), HttpStatus.BAD_REQUEST);
        }

    }

}
