package com.uit.payment;

import com.uit.dto.request.InfoVietQrReq;
import com.uit.dto.response.InfoVietQrResponse;
import com.uit.dto.response.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "VietQRService", url = "${payment.vqr.base-url}")
public interface FeignClientVietQrService {

    @PostMapping(value ="/vqr/api/token_generate", consumes = "application/json")
    ResponseEntity<TokenResponse> getTokenGenerateQR(
            @RequestHeader("Authorization") String token
    );

    @PostMapping(value = "/vqr/api/qr/generate-customer", consumes = "application/json")
    ResponseEntity<InfoVietQrResponse> generateQR(
            @RequestHeader("Authorization") String token,
            @RequestBody InfoVietQrReq request
    );
}
