package com.uit.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InfoVietQrReq {

    private double amount;

    private String content;

    private String bankAccount;

    private String bankCode;

    private String userBankName;

    private String transType;

    private String qrType;

    private String orderId;

    //optional
    private String sign;
    private String terminalCode;
    private String urlLink;
    private String serviceCode;
    private String subTerminalCode;

}
