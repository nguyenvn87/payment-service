package com.uit.payment.service;

import com.uit.dto.request.InfoTransactionReq;
import com.uit.entity.Order;

import java.time.LocalDateTime;

public interface OrderService {
    Order createOrderBive(String orderId, InfoTransactionReq infoTransactionReq, LocalDateTime time);
    Order createOrderPodcast(String orderId, InfoTransactionReq infoTransactionReq, LocalDateTime time);
}
