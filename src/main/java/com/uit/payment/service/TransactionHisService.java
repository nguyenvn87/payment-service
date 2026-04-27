package com.uit.payment.service;

import com.uit.dto.request.TransactionCallback;
import com.uit.entity.Order;

public interface TransactionHisService {
    void createTransactionHistory(Order order, TransactionCallback transactionCallback);
}
