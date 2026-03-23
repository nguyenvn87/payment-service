package com.uit.dto;

import lombok.Data;

@Data
public class TransactionCallback {

    private String transactionid;
    private long transactiontime;
    private String referencenumber;
    private double amount;
    private String content;
    private String bankaccount;
    private String orderId;
    private String sign;
    private String terminalCode;
    private String urlLink;
    private String serviceCode;
    private String subTerminalCode;
}
