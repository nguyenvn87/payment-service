package com.uit.dto.response;

import java.util.List;

public record InfoVietQrResponse(
        String bankCode,
        String bankName,
        String bankAccount,
        String userBankName,
        String amount,
        String content,
        String qrCode,
        String imgId,
        Integer existing,
        String transactionId,
        String transactionRefId,
        String qrLink,
        String terminalCode,
        String subTerminalCode,
        String serviceCode,
        String orderId,
        List<Object> additionalData,
        String vaAccount
) {}
