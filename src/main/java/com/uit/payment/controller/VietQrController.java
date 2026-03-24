package com.uit.payment.controller;

import com.uit.dto.request.InfoVietQrReq;
import com.uit.dto.response.TokenResponse;
import com.uit.payment.FeignClientVietQrService;
import com.uit.payment.service.VietQrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/vqr")
public class VietQrController {

    @Autowired
    private VietQrService vietQrService;

    @PostMapping("/generate/token-qr")
    public ResponseEntity<?> generateToken() {

        log.info("==================== Start generate token qr code  ========================");
        try {
            TokenResponse response = vietQrService.getTokenToCallQR();
            log.info("==================== generate token qr code  ========================" + response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/generate-qr")
    public ResponseEntity<?> generateQr(@RequestHeader("Authorization") String authHeader,
                                        @RequestBody InfoVietQrReq infoVietQrReq) {

        try {
            log.info("==================== Start generate qr code  ========================");
            String response = vietQrService.generateQR(authHeader, infoVietQrReq);
            log.info("==================== generate qr code  ========================" + authHeader);
            log.info("==================== generate qr code  ========================" + infoVietQrReq.toString());
            log.info("==================== generate qr code  ========================" + response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
