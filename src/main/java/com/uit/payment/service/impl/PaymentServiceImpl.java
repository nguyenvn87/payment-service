package com.uit.payment.service.impl;

import com.uit.common.constant.PaymentStsEnums;
import com.uit.dto.request.TransactionCallback;
import com.uit.entity.Order;
import com.uit.payment.repository.OrderRepository;
import com.uit.payment.service.PaymentService;
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

        Optional<Order> order = orderRepository.findById(transactionCallback.getOrderId());
        order.ifPresent(o -> {
            o.setPayStatus(PaymentStsEnums.PayCompleted);
            o.setPayedMoney(transactionCallback.getAmount());
            orderRepository.save(o);
        });
    }
}
