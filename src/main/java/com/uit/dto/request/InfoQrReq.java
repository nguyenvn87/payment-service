package com.uit.dto.request;

import lombok.Data;

@Data
public class InfoQrReq {

    private String bankCode;          // mã_ngân_hàng
    private String bankName;          // tên_ngân_hàng
    private String bankAccount;       // tài_khoản ngân hàng nhận
    private String userBankName;      // tên chủ tài khoản
    private String amount;            // số tiền cần thanh toán
    private String content;           // nội dung thanh toán
    private String qrCode;            // mã QR dạng string
    private String imgId;             // id ảnh ngân hàng
    private String existing;          // trạng thái (1: đã tạo)
    private String transactionId;     // id định danh của QR
    private String transactionRefId;  // mã định danh của QR
    private String qrLink;            // mã QR dạng link
    private String terminalCode;      // mã điểm bán
    private String subTerminalCode;   // mã con điểm bán
    private String serviceCode;       // mã sản phẩm
    private String orderId;           // mã đơn hàng
    private String additionalData;    // thông tin thêm
    private String vaAccount;         // Virtual Account
}
