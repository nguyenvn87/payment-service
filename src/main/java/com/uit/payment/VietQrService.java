package com.uit.payment;

import com.uit.dto.request.InfoQrReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "VietQRService", url = "https://api.example.com")
public interface VietQrService {

    @PostMapping("https://dev.vietqr.org/vqr/api/qr/generate-customer")
    String generateTokenGetQR(
            @RequestHeader("Authorization") String token,
            @RequestHeader(MediaType.APPLICATION_JSON_VALUE)
            @RequestBody InfoQrReq request
    );
}
