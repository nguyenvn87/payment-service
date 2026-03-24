package com.uit.common.exceptions;

import lombok.Getter;

@Getter
public enum PaymentError {

    // ===== CLIENT ERROR =====
    INVALID_REQUEST("FAIL","01", "400", "Dữ liệu request không hợp lệ"),
    MISSING_FIELD("FAIL","02", "400", "Thiếu field bắt buộc"),
    INVALID_AMOUNT("FAIL","03", "400", "Số tiền không hợp lệ"),

    // ===== AUTH ERROR =====
    UNAUTHORIZED("FAIL","04", "401", "Sai username hoặc password"),
    TOKEN_EXPIRED("FAIL","05", "401", "Token đã hết hạn"),

    // ===== VIETQR ERROR =====
    VIETQR_ERROR("FAIL","06", "502", "Lỗi từ hệ thống VietQR"),
    VIETQR_TIMEOUT("FAIL","07", "504", "Timeout khi gọi VietQR"),

    // ===== SYSTEM ERROR =====
    INTERNAL_ERROR("FAIL","99", "500", "Lỗi hệ thống");

    private final String error;
    private final String errorCode;
    private final String errorStatusCode;
    private final String errorMessage;

    PaymentError(String error, String errorCode, String errorStatusCode, String errorMessage) {
        this.error = error;
        this.errorCode = errorCode;
        this.errorStatusCode = errorStatusCode;
        this.errorMessage = errorMessage;
    }

}
