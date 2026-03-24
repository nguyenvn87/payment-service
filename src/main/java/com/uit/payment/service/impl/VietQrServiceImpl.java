package com.uit.payment.service.impl;

import com.uit.config.JwtUtil;
import com.uit.dto.request.InfoVietQrReq;
import com.uit.dto.response.InfoVietQrRes;
import com.uit.dto.response.TokenResponse;
import com.uit.payment.FeignClientVietQrService;
import com.uit.payment.service.VietQrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class VietQrServiceImpl implements VietQrService {

    @Value("${payment.client.username}")
    private String CLIENT_USERNAME;
    @Value("${payment.client.username}")
    private String CLIENT_PASSWORD; ;

    private final FeignClientVietQrService feignClientVietQrService;
    private final JwtUtil jwtUtil;

    public VietQrServiceImpl(FeignClientVietQrService feignClientVietQrService, JwtUtil jwtUtil) {
        this.feignClientVietQrService = feignClientVietQrService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public TokenResponse getTokenToCallQR(){

        ResponseEntity<TokenResponse> response = feignClientVietQrService.getTokenGenerateQR(
                jwtUtil.basicAuth(CLIENT_USERNAME,CLIENT_PASSWORD));
        log.info("Call to api get token VietQr : {}", response.getStatusCode());
        if (response.getStatusCode().is2xxSuccessful()){
            return response.getBody();
        }
        return response.getBody();
    }

    @Override
    public String generateQR(String authHeader, InfoVietQrReq infoVietQrReq) {

        ResponseEntity<InfoVietQrRes> response = feignClientVietQrService.generateQR(authHeader, infoVietQrReq);
        log.info("Call to api get QR code : {}", response.getStatusCode());
        if (response.getStatusCode().is2xxSuccessful()){
            return Objects.requireNonNull(response.getBody()).qrLink();
        }
        return "";
    }

}
