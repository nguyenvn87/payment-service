package com.uit.payment;

import com.uit.dto.request.InfoVietQrReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "VietQRService", url = "https://dev.vietqr.org")
public interface VietQrService {

    @PostMapping("/vqr/api/token_generate")
    String getTokenGenerateQR(
            @RequestHeader("Authorization") String token,
            @RequestHeader("Content-Type") String contentType
    );

    @PostMapping(value = "/vqr/api/qr/generate-customer", consumes = "application/json")
    String generateQR(
            @RequestHeader("Authorization") String token,
            @RequestBody InfoVietQrReq request
    );
}
