package com.uit.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class InfoVietQrReq {

    // ===== Required =====
    private String bankCode;        // Mã ngân hàng
    private String bankAccount;     // Số tài khoản
    private String userBankName;    // Tên chủ TK (không dấu)
    private String content;         // Nội dung chuyển tiền (<=23 ký tự)
    private Integer qrType;         // 0, 1, 3

    // ===== Conditional Required =====
    private Long amount;            // bắt buộc nếu qrType = 0 hoặc 3
    private String orderId;         // bắt buộc nếu qrType = 0 (<=13 ký tự)
    private String transType = "C"; // mặc định C, bắt buộc nếu qrType = 0

    private String terminalCode;    // bắt buộc nếu qrType = 1 hoặc 3
    private String serviceCode;     // bắt buộc nếu qrType = 3

    // ===== Optional =====
    private String subTerminalCode;
    private String sign;
    private String urlLink;
    private String note;

    private List<Object> additionalData;
}
