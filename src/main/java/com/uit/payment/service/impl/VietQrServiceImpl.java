package com.uit.payment.service.impl;

import com.uit.common.exceptions.PaymentError;
import com.uit.common.exceptions.PaymentException;
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

@Slf4j
@Service
public class VietQrServiceImpl implements VietQrService {

    @Value("${payment.client.username}")
    private String CLIENT_USERNAME;
    @Value("${payment.client.password}")
    private String CLIENT_PASSWORD; ;

    private final FeignClientVietQrService feignClientVietQrService;
    private final JwtUtil jwtUtil;

    public VietQrServiceImpl(FeignClientVietQrService feignClientVietQrService, JwtUtil jwtUtil) {
        this.feignClientVietQrService = feignClientVietQrService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public TokenResponse getTokenToCallQR() {

        String basicAuth = jwtUtil.basicAuth(CLIENT_USERNAME, CLIENT_PASSWORD);

        ResponseEntity<TokenResponse> response =
                feignClientVietQrService.getTokenGenerateQR(basicAuth);

        log.info("Call to api get token VietQr : {}", response.getStatusCode());

        // ✅ Check HTTP status
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new PaymentException(PaymentError.VIETQR_CALL_API_FAIL);
        }

        // ✅ Check body null
        TokenResponse body = response.getBody();
        if (body == null) {
            throw new PaymentException(PaymentError.VIETQR_RESPONSE_BODY_NULL);
        }

        // ✅ Check access_token
        if (body.getAccess_token() == null || body.getAccess_token().isBlank()) {
            throw new PaymentException(PaymentError.VIETQR_ACCESS_TOKEN_NULL);
        }

        return body;
    }

    @Override
    public String generateQR(InfoVietQrReq infoVietQrReq) {

        TokenResponse tokenResponse = getTokenToCallQR();
        String accessToken = tokenResponse.getAccess_token();

        ResponseEntity<InfoVietQrRes> response =
                feignClientVietQrService.generateQR("Bearer " + accessToken, infoVietQrReq);

        log.info("Call to api get QR code : {}", response.getStatusCode());

        // ✅ Check HTTP status
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new PaymentException(PaymentError.VIETQR_ACCESS_TOKEN_NULL);
        }

        // ✅ Check body null
        InfoVietQrRes body = response.getBody();
        if (body == null) {
            throw new PaymentException(PaymentError.VIETQR_GETQR_RESPONSE_BODY_NULL);
        }

        // ✅ Check dữ liệu quan trọng
        if (body.qrLink() == null || body.qrLink().isBlank()) {
            throw new PaymentException(PaymentError.VIETQR_GETQR_NULL);
        }

        return body.qrLink();
    }

}
