package com.uit.payment.service.impl;

import com.uit.common.constant.PaymentStsEnums;
import com.uit.common.constant.PurchaseTypeEnums;
import com.uit.dto.request.InfoTransactionReq;
import com.uit.dto.request.TransactionCallback;
import com.uit.entity.Order;
import com.uit.entity.OrderDetail;
import com.uit.entity.TopupHistory;
import com.uit.payment.repository.OrderRepository;
import com.uit.payment.repository.TransactionRepository;
import com.uit.payment.service.OrderService;
import com.uit.payment.service.TransactionHisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionHisServiceImpl implements TransactionHisService {

    private static final Logger log = LoggerFactory.getLogger(TransactionHisServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void createTransactionHistory(Order order, TransactionCallback transactionCallback){
        TopupHistory trans = new TopupHistory();
        trans.setOrderId(order.getOrderId());
        trans.setBillCd(order.getBillCode());
        trans.setAmountValue(order.getTotalMoney());
        trans.setPointAdded(0);
        trans.setMessage(transactionCallback.getContent());
        transactionRepository.save(trans);
    }
}
