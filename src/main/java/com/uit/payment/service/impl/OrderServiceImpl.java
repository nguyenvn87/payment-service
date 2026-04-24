package com.uit.payment.service.impl;

import com.uit.common.HmacUtil;
import com.uit.common.TimeUtils;
import com.uit.common.constant.PaymentStsEnums;
import com.uit.common.constant.PurchaseTypeEnums;
import com.uit.common.constant.ServiceTypeEnums;
import com.uit.common.exceptions.PaymentError;
import com.uit.common.exceptions.PaymentException;
import com.uit.config.CompactEncoder;
import com.uit.dto.request.DataSyncBankReq;
import com.uit.dto.request.InfoTransactionReq;
import com.uit.dto.request.TransactionCallback;
import com.uit.dto.response.TokenResponse;
import com.uit.entity.Order;
import com.uit.entity.OrderDetail;
import com.uit.payment.FeignClientBiveService;
import com.uit.payment.FeignClientPodcastService;
import com.uit.payment.repository.OrderRepository;
import com.uit.payment.service.OrderService;
import com.uit.payment.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrderBive(String orderId, InfoTransactionReq infoTransactionReq, LocalDateTime time){
        Order order = Order.builder()
                .orderId(orderId)
                .billCode("MHD")
                .createDate(time)
                .payStatus(PaymentStsEnums.Pending)
                .payedMoney(0)
                .phone(infoTransactionReq.getPhone())
                .purchaseType(PurchaseTypeEnums.BANKQR)
                .ref(infoTransactionReq.getRefCode())
                .totalMoney(infoTransactionReq.getAmount())
                .userId(infoTransactionReq.getUserId())
                .serviceType(infoTransactionReq.getServiceType())
                .flag("NORMAL")
                .build();
        OrderDetail detail = new OrderDetail();
        detail.setOrder(order);
        detail.setAmount(infoTransactionReq.getAmount());
        if(infoTransactionReq.getServiceType()== ServiceTypeEnums.PACKAGE)
            detail.setServiceType(ServiceTypeEnums.PACKAGE);
        else detail.setServiceType(ServiceTypeEnums.EXTENT);
        order.setDetails(List.of(detail));
        return orderRepository.save(order);
    }
    @Override
    public Order createOrderPodcast(String orderId, InfoTransactionReq infoTransactionReq, LocalDateTime time){

        return null;
    }
}
