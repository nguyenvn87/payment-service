package com.uit.payment.service.impl;

import com.uit.common.HmacUtil;
import com.uit.common.TimeUtils;
import com.uit.common.constant.PaymentStsEnums;
import com.uit.common.constant.ServiceTypeEnums;
import com.uit.common.exceptions.PaymentError;
import com.uit.common.exceptions.PaymentException;
import com.uit.config.CompactEncoder;
import com.uit.dto.request.DataSyncBankReq;
import com.uit.dto.request.DeductRequest;
import com.uit.dto.request.TransactionCallback;
import com.uit.dto.response.DeductResponse;
import com.uit.dto.response.TokenResponse;
import com.uit.entity.Order;
import com.uit.entity.TopupHistory;
import com.uit.entity.UserWallet;
import com.uit.payment.FeignClientBiveService;
import com.uit.payment.FeignClientPodcastService;
import com.uit.payment.repository.OrderRepository;
import com.uit.payment.repository.ToptupRepository;
import com.uit.payment.repository.UserWalletRepository;
import com.uit.payment.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${payment.signature}")
    private String SIGNER_KEY; ;

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final OrderRepository orderRepository;
    private final FeignClientBiveService feignClientBiveService;
    private final FeignClientPodcastService feignClientPodcastService;
    private final ToptupRepository toptupRepository;
    private final UserWalletRepository walletRepository;


    public PaymentServiceImpl(OrderRepository orderRepository, FeignClientBiveService feignClientBiveService, FeignClientPodcastService feignClientPodcastService, ToptupRepository toptupRepository, UserWalletRepository walletRepository) {
        this.orderRepository = orderRepository;
        this.feignClientBiveService = feignClientBiveService;
        this.feignClientPodcastService = feignClientPodcastService;
        this.toptupRepository = toptupRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    @Transactional
    public void updateInformationPayment(TransactionCallback transactionCallback) {

        //DONE áp dụng khi có chữ kí trả ve
        if(!HmacUtil.verify(transactionCallback.getOrderId(),SIGNER_KEY,transactionCallback.getSign())){
            throw new PaymentException(PaymentError.WRONG_FAIL_SINGER_KEY);
        }

        log.info("=============== update payment information ======================");
        log.info("=============== Transaction Id ====================== " + transactionCallback.getOrderId());
        CompactEncoder.DecodedData data = CompactEncoder.decode(transactionCallback.getOrderId());
        log.info(data.uuid().toString());
        log.info(data.extraData() + "");

        log.info("=============== updateInformationPayment ====================== " + transactionCallback.getOrderId());
        Order order = orderRepository.findById(data.uuid().toString())
                .orElseThrow(() -> new PaymentException(PaymentError.NOT_HAVE_TRANSACTION_PAYMENT));

        log.info("=============== updateInformationPayment ====================== " + TimeUtils.getCurrentTime(order.getCreateDate()));
        log.info("=============== updateInformationPayment ====================== " + data.extraData());
        if (TimeUtils.getCurrentTime(order.getCreateDate()) != data.extraData()) {
            throw new PaymentException(PaymentError.WRONG_DATA_TRANSACTION);
        }

        log.info("=============== updateInformationPayment ====================== " + order.getTotalMoney());
        log.info("=============== updateInformationPayment ====================== " + transactionCallback.getAmount());
        if (order.getTotalMoney() != transactionCallback.getAmount()){
            order.setFlag("WARNING");
        }

        log.info("=============== updateInformationPayment ====================== " + PaymentStsEnums.PayCompleted.name());
        order.setPayStatus(PaymentStsEnums.PayCompleted);
        log.info("=============== updateInformationPayment ====================== " + transactionCallback.getAmount());
        order.setPayedMoney(transactionCallback.getAmount());
        orderRepository.save(order);

        //Save to toptupHistory
        TopupHistory toptupHistory = TopupHistory.builder()
                .order(order)
                .createDate( TimeUtils.getCurrentTimeWithLocalDateTime())
                .amountValue(transactionCallback.getAmount())

                //TODO will add later
                .pointAdded(1.0)
                .build();
        toptupRepository.save(toptupHistory);

        log.info("=============== payment information updated successfully ====================");
        DataSyncBankReq dataSyncBankReq = DataSyncBankReq.builder()
                    .userId(transactionCallback.getSubTerminalCode())
                    .price(transactionCallback.getAmount()).build();
        syneDataToService(transactionCallback.getTerminalCode(),dataSyncBankReq);
//        if (transactionCallback.getTerminalCode().equals(ServiceTypeEnums.BIVEEDU.name())){
//
//            DataSyncBankReq dataSyncBankReq = DataSyncBankReq.builder()
//                    .userId(transactionCallback.getSubTerminalCode())
//                    .price(transactionCallback.getAmount()).build();
//            ResponseEntity<TokenResponse> response = feignClientBiveService.syneDataToService(dataSyncBankReq);
//            log.info("=============== Sync data to service ====================");
//            log.info("Sync data for user : {} with amount : {} ", transactionCallback.getSubTerminalCode(), transactionCallback.getAmount());
//            log.info("Response with status : {} ", response.getStatusCode());
//        }

    }

    private void syneDataToService(String serviceType, DataSyncBankReq dataSyncBankReq) {
        log.info("=============== Sync data to service ====================");
        ResponseEntity<TokenResponse> response = null;
        switch (serviceType) {
            case "BIVEEDU":
                response = feignClientBiveService.syneDataToService(dataSyncBankReq);
                log.info("Response with status : {} ", response.getStatusCode());
                break;
            case "PODCAST":
                response = feignClientPodcastService.syneDataToService(dataSyncBankReq);
                break;
            default:
                log.warn("Unknown service type: {}", serviceType);
        }
        log.info("=============== Sync data to service with status  =====================" + response.getStatusCode());
    }

    @Transactional
    public DeductResponse deductMoney(DeductRequest request) {

        UserWallet wallet = walletRepository.findByUserIdForUpdate(request.getUserId());
        if (wallet == null) {
            wallet.setUserId(request.getUserId());
            wallet.setBalanceMoney(BigDecimal.ZERO);
            walletRepository.save(wallet);
            log.info("New wallet created for user {}", request.getUserId());
        }

        BigDecimal currentBalance = wallet.getBalanceMoney();

        // validate
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        if (currentBalance.compareTo(request.getAmount()) < 0) {

            //TODO return new payment exception
            return DeductResponse.builder()
                    .userId(request.getUserId())
                    .remainBalance(currentBalance)
                    .status("FAILED_INSUFFICIENT_BALANCE")
                    .build();
        }

        // deduct
        BigDecimal newBalance = currentBalance.subtract(request.getAmount());
        wallet.setBalanceMoney(newBalance);

        walletRepository.save(wallet);

        return DeductResponse.builder()
                .userId(request.getUserId())
                .remainBalance(newBalance)
                .status("SUCCESS")
                .build();
    }
}
