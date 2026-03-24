package com.uit.dto.request;

import lombok.Data;

@Data
public class InfoVietQrReq {

    private Long amount;

    private String content;

    private String bankAccount;

    private String bankCode;

    private String userBankName;

    private String transType;

    private String qrType;

}
