package com.uit.payment.service.impl;

import com.uit.common.HmacUtil;
import com.uit.common.JsonUtil;
import com.uit.common.TimeUtils;
import com.uit.common.constant.PaymentStsEnums;
import com.uit.common.constant.PurchaseTypeEnums;
import com.uit.common.constant.ServiceTypeEnums;
import com.uit.common.exceptions.PaymentError;
import com.uit.common.exceptions.PaymentException;
import com.uit.config.CommonAuthUtils;
import com.uit.config.CompactEncoder;
import com.uit.dto.request.DataSyncBankReq;
import com.uit.dto.request.InfoTransactionReq;
import com.uit.dto.request.InfoVietQrReq;
import com.uit.dto.response.InfoVietQrRes;
import com.uit.dto.response.QrCodeRes;
import com.uit.dto.response.TokenResponse;
import com.uit.entity.Order;
import com.uit.payment.FeignClientSyncDataService;
import com.uit.payment.FeignClientVietQrService;
import com.uit.payment.repository.OrderRepository;
import com.uit.payment.service.VietQrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import static com.uit.config.CommonAuthUtils.BEARER_PREFIX;

@Slf4j
@Service
public class VietQrServiceImpl implements VietQrService {

    @Value("${payment.client.username}")
    private String CLIENT_USERNAME;
    @Value("${payment.client.password}")
    private String CLIENT_PASSWORD; ;
    @Value("${payment.signature}")
    private String SIGNER_KEY; ;

    private final String BANK_ACCOUNT = "8883565290";
    private final String BANK_CODE = "BIDV";
    private final String USER_BANK_NAME = "NGUYEN VAN NGUYEN";
    private final String TRANS_TYPE = "C";
    private final String QR_TYPE = "0";

    private final FeignClientVietQrService feignClientVietQrService;
    private final FeignClientSyncDataService feignClientSyncDataService;
    private final CommonAuthUtils commonAuthUtils;
    private final OrderRepository orderRepository;

    public VietQrServiceImpl(FeignClientVietQrService feignClientVietQrService, FeignClientSyncDataService feignClientSyncDataService, CommonAuthUtils commonAuthUtils, OrderRepository orderRepository) {
        this.feignClientVietQrService = feignClientVietQrService;
        this.feignClientSyncDataService = feignClientSyncDataService;
        this.commonAuthUtils = commonAuthUtils;
        this.orderRepository = orderRepository;
    }

    @Override
    public TokenResponse getTokenToCallQR() {

        String basicAuth = commonAuthUtils.basicAuth(CLIENT_USERNAME, CLIENT_PASSWORD);

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
    @Transactional
    public QrCodeRes generateQR(InfoTransactionReq infoTransactionReq) {

        LocalDateTime time = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        String oderId = UUID.randomUUID().toString();
        String oderIdEncode = CompactEncoder.encode(oderId, TimeUtils.getCurrentTime(time));
        //DONE áp dụng khi có chữ kí trả ve
        String sign = HmacUtil.generateSignature(oderIdEncode,SIGNER_KEY);
        InfoVietQrReq infoVietQrReq = InfoVietQrReq.builder()
                .amount(infoTransactionReq.getAmount())
                .bankAccount(BANK_ACCOUNT)
                .bankCode(BANK_CODE)
                .userBankName(USER_BANK_NAME)
                .transType(TRANS_TYPE)
                .qrType(QR_TYPE)
                .content(infoTransactionReq.getUserId())
                .orderId(oderIdEncode)
//                .sign("dummy")
                //DONE áp dụng khi có chữ kí trả ve
                .sign(sign)
                .terminalCode(infoTransactionReq.getServiceType().name())
                .urlLink("dummy2")
                .serviceCode("dummy3")
                .subTerminalCode("dummy4")
                .build();
        log.info("============= InfoTransactionReq =================");
        log.info(JsonUtil.toJson(infoVietQrReq));

        TokenResponse tokenResponse = getTokenToCallQR();
        String accessToken = tokenResponse.getAccess_token();

        log.info("===================================== " );
        log.info(accessToken);
        log.info("===================================== " );

        ResponseEntity<InfoVietQrRes> response =
                feignClientVietQrService.generateQR(BEARER_PREFIX + accessToken, infoVietQrReq);

        log.info("Call to api get QR code : {}", response.getStatusCode());

        // ✅ Check HTTP status
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new PaymentException(PaymentError.VIETQR_CALL_API_FAIL);
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

        // ✅ Insert data to DB
        Order order = Order.builder()
                .orderId(oderId)
                .billCode("MHD")
                .createDate(time)
                .payStatus(PaymentStsEnums.Pending)
                .payedMoney(0)
                .phone(infoTransactionReq.getPhone())
                .purchaseType(PurchaseTypeEnums.CASH)
                .ref(infoTransactionReq.getRefCode())
                .totalMoney(infoTransactionReq.getAmount())
                .userId(infoTransactionReq.getUserId())
                .serviceType(infoTransactionReq.getServiceType())
                .flag("NORMAL")
                .build();
        orderRepository.save(order);
        log.info("============= Order =================");
//        log.info(JsonUtil.toJson(order));

        if (infoTransactionReq.getServiceType().equals(ServiceTypeEnums.BIVEEDU))
        {
            DataSyncBankReq dataSyncBankReq = DataSyncBankReq.builder()
                    .userId(infoTransactionReq.getUserId())
                    .pac(infoTransactionReq.getPackageType().name())
                    .price(infoTransactionReq.getAmount()).build();
            feignClientSyncDataService.syneDataToService(dataSyncBankReq);
        }

        return new QrCodeRes(body.qrLink(),body.content());
    }

}
