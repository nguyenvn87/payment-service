package com.uit.payment.controller;

import com.uit.dto.request.InfoVietQrReq;
import com.uit.dto.response.TokenResponse;
import com.uit.payment.VietQrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@RestController
@RequestMapping("api/v1/vqr")
public class VietQrController {

    @Autowired
    private VietQrService vietQrService;

    @PostMapping("/generate/token-qr")
    public ResponseEntity<?> generateToken(@RequestHeader("Authorization") String authHeader) {

        log.info("==================== Start generate token qr code  ========================");
        String response = vietQrService.getTokenGenerateQR(authHeader, MediaType.APPLICATION_JSON_VALUE);
        log.info("==================== generate token qr code  ========================" + authHeader);
        log.info("==================== generate token qr code  ========================" + MediaType.APPLICATION_JSON_VALUE);
        log.info("==================== generate token qr code  ========================" + response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/generate-qr")
    public ResponseEntity<?> generateQr(@RequestHeader("Authorization") String authHeader,
                                        @RequestBody InfoVietQrReq infoVietQrReq) {

        log.info("==================== Start generate qr code  ========================");
        String response = vietQrService.generateQR(authHeader, MediaType.APPLICATION_JSON_VALUE,infoVietQrReq);
        log.info("==================== generate qr code  ========================" + authHeader);
        log.info("==================== generate qr code  ========================" + infoVietQrReq.toString());
        log.info("==================== generate qr code  ========================" + response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
