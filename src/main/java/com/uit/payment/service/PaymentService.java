package com.uit.payment.service;

import com.uit.dto.request.DeductRequest;
import com.uit.dto.request.TransactionCallback;
import com.uit.dto.response.DeductResponse;

public interface PaymentService {

    void updateInformationPayment(TransactionCallback transactionCallback);

    DeductResponse deductMoney(DeductRequest request);
}
