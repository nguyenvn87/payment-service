package com.uit.payment.service;

import com.uit.dto.request.InfoTransactionReq;
import com.uit.dto.request.InfoVietQrReq;
import com.uit.dto.response.QrCodeRes;
import com.uit.dto.response.TokenResponse;

public interface VietQrService {

    TokenResponse getTokenToCallQR();

    QrCodeRes generateQR(InfoTransactionReq infoTransactionReq);

}
