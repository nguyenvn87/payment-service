package com.uit.payment.controller;

import com.uit.common.JsonUtil;
import com.uit.common.constant.ServiceTypeEnums;
import com.uit.common.exceptions.PaymentSuccess;
import com.uit.config.ServerClientProperties;
import com.uit.dto.request.InfoTransactionReq;
import com.uit.dto.request.InfoVietQrReq;
import com.uit.dto.response.QrCodeRes;
import com.uit.dto.response.TokenResponse;
import com.uit.payment.FeignClientVietQrService;
import com.uit.payment.service.VietQrService;
import jakarta.validation.Valid;
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

    @Autowired
    private ServerClientProperties serverClientProperties;


//    @PostMapping("/generate/token-qr")
//    public ResponseEntity<?> generateToken() {
//
//        log.info("==================== Start generate token qr code  ========================");
//        try {
//            TokenResponse response = vietQrService.getTokenToCallQR();
//            log.info("==================== generate token qr code  ========================" + response);
//            return new ResponseEntity<>(new PaymentSuccess(response), HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/generate-qr")
    public ResponseEntity<?> generateQr(@Valid @RequestBody InfoTransactionReq infoTransactionReq) {

        log.info("==================== Start generate qr code  ========================");
        log.info(JsonUtil.toJson(infoTransactionReq));
        if (infoTransactionReq.getServiceType().equals(ServiceTypeEnums.BIVEEDU)){
            if (infoTransactionReq.getPackageType().equals(ServiceTypeEnums.PACKAGE1)) {
                infoTransactionReq.setAmount(serverClientProperties.getBive().getPack().get(0));
            }
            if (infoTransactionReq.getPackageType().equals(ServiceTypeEnums.PACKAGE2)) {
                infoTransactionReq.setAmount(serverClientProperties.getBive().getPack().get(1));
            }
            if (infoTransactionReq.getPackageType().equals(ServiceTypeEnums.PACKAGE3)) {
                infoTransactionReq.setAmount(serverClientProperties.getBive().getPack().get(2));
            }
        }
        QrCodeRes response = vietQrService.generateQR(infoTransactionReq);
        log.info("==================== generate qr code  ========================" + JsonUtil.toJson(response));
        return new ResponseEntity<>(new PaymentSuccess(response), HttpStatus.OK);

    }

}
