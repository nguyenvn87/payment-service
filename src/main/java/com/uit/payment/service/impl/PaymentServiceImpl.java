package com.uit.payment.service.impl;

import com.uit.common.JsonUtil;
import com.uit.common.TimeUtils;
import com.uit.common.constant.PaymentStsEnums;
import com.uit.common.exceptions.PaymentError;
import com.uit.common.exceptions.PaymentException;
import com.uit.config.CompactEncoder;
import com.uit.dto.request.TransactionCallback;
import com.uit.entity.Order;
import com.uit.payment.repository.OrderRepository;
import com.uit.payment.service.PaymentService;
import com.uit.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);
    private final OrderRepository orderRepository;

    public PaymentServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void updateInformationPayment(TransactionCallback transactionCallback) {
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

        log.info("=============== payment information updated successfully ====================");

    }
}
