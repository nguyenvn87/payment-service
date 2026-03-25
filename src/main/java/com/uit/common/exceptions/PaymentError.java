package com.uit.common.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PaymentError {

    // ===== VIETQR ERROR =====
    VIETQR_CALL_API_FAIL("FAIL","VQR1", HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi call từ hệ thống VietQR"),
    VIETQR_RESPONSE_BODY_NULL("FAIL","VQR2", HttpStatus.INTERNAL_SERVER_ERROR, "Data trả về từ hệ thông bị null"),
    VIETQR_ACCESS_TOKEN_NULL("FAIL","VQR3", HttpStatus.INTERNAL_SERVER_ERROR, "Không có Access token từ hệ thống VietQR"),
    VIETQR_GETQR_RESPONSE_BODY_NULL("FAIL","VQR2", HttpStatus.INTERNAL_SERVER_ERROR, "Data trả về từ hệ thông bị null khi gọi VietQR"),
    VIETQR_GETQR_NULL("FAIL","VQR3", HttpStatus.INTERNAL_SERVER_ERROR, "Hệ thôńg không trả về QR Code"),

//     ===== CLIENT ERROR =====
    PAYMENT_ERROR_DURING_TRANSACTION("FAIL","CL2", HttpStatus.BAD_REQUEST, "Đã xảy ra lỗi trong quá trình giao dịch");
//    MISSING_FIELD("FAIL","02", HttpStatus.INTERNAL_SERVER_ERROR, "Thiếu field bắt buộc"),
//    INVALID_AMOUNT("FAIL","03", HttpStatus.INTERNAL_SERVER_ERROR, "Số tiền không hợp lệ"),

    // ===== AUTH ERROR =====
//    UNAUTHORIZED("FAIL","04", HttpStatus.INTERNAL_SERVER_ERROR, "Sai username hoặc password"),
//    TOKEN_EXPIRED("FAIL","05", HttpStatus.INTERNAL_SERVER_ERROR, "Token đã hết hạn"),

    // ===== VIETQR ERROR =====
//    VIETQR_CALL_API_FAIL("FAIL","VQR", HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi từ hệ thống VietQR"),
//    VIETQR_TIMEOUT("FAIL","VQR", HttpStatus.INTERNAL_SERVER_ERROR, "Timeout khi gọi VietQR"),
//    VIETQR_ERROR("FAIL","VQR", HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi từ hệ thống VietQR"),
//    VIETQR_ERROR1("FAIL","VQR", HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi từ hệ thống VietQR"),
//    VIETQR_ERROR2("FAIL","VQR", HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi từ hệ thống VietQR"),

    // ===== SYSTEM ERROR =====
//    INTERNAL_ERROR("FAIL","99", HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi hệ thống");

    private final String result;
    private final String errorCode;
    private final HttpStatus errorStatusCode;
    private final String errorMessage;

    PaymentError(String result, String errorCode, HttpStatus errorStatusCode, String errorMessage) {
        this.result = result;
        this.errorCode = errorCode;
        this.errorStatusCode = errorStatusCode;
        this.errorMessage = errorMessage;
    }

}
