package com.uit.common.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PaymentError {

    // ===== VIETQR ERROR =====
    VIETQR_CALL_API_FAIL("FAIL","VQR1", HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi call tới hệ thống VietQR"),
    VIETQR_RESPONSE_BODY_NULL("FAIL","VQR2", HttpStatus.INTERNAL_SERVER_ERROR, "Data trả về từ hệ thông bị null"),
    VIETQR_ACCESS_TOKEN_NULL("FAIL","VQR3", HttpStatus.INTERNAL_SERVER_ERROR, "Không có Access token từ hệ thống VietQR"),
    VIETQR_GETQR_RESPONSE_BODY_NULL("FAIL","VQR2", HttpStatus.INTERNAL_SERVER_ERROR, "Data trả về từ hệ thông bị null khi gọi VietQR"),
    VIETQR_GETQR_NULL("FAIL","VQR3", HttpStatus.INTERNAL_SERVER_ERROR, "Hệ thôńg không trả về QR Code"),

//     ===== CLIENT ERROR =====
    PAYMENT_ERROR_DURING_TRANSACTION("FAIL","CL2", HttpStatus.BAD_REQUEST, "Đã xảy ra lỗi trong quá trình giao dịch"),
    NOT_HAVE_TRANSACTION_PAYMENT("FAIL","CL3", HttpStatus.BAD_REQUEST, "Đã xảy ra lỗi trong quá trình giao dịch"),
//    MISSING_FIELD("FAIL","02", HttpStatus.INTERNAL_SERVER_ERROR, "Thiếu field bắt buộc"),
//    INVALID_AMOUNT("FAIL","03", HttpStatus.INTERNAL_SERVER_ERROR, "Số tiền không hợp lệ"),

    // ===== ENCRYPT ERROR =====
    ENCODE_DATA_FAIL ("FAIL","EE1", HttpStatus.BAD_REQUEST, "xảy ra lỗi trong quá trình mã hóa"),
    DECODE_DATA_FAIL ("FAIL","EE2", HttpStatus.BAD_REQUEST, "xảy ra lỗi trong quá trình giải mã"),
    WRONG_DATA_TRANSACTION ("FAIL","EE3", HttpStatus.BAD_REQUEST, "xảy ra lỗi trong quá trình giải mã"),

    // ===== VALID DATA =====
    WRONG_FAIL_SINGER_KEY("FAIL","SIG", HttpStatus.BAD_REQUEST, "sai chữ kí"),
//    VIETQR_TIMEOUT("FAIL","VQR", HttpStatus.INTERNAL_SERVER_ERROR, "Timeout khi gọi VietQR"),
//    VIETQR_ERROR("FAIL","VQR", HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi từ hệ thống VietQR"),
//    VIETQR_ERROR1("FAIL","VQR", HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi từ hệ thống VietQR"),
//    VIETQR_ERROR2("FAIL","VQR", HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi từ hệ thống VietQR"),

    // ===== SYSTEM ERROR =====
//    INTERNAL_ERROR("FAIL","99", HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi hệ thống");

    // ===== SIGNATURE =====
    INVALID_SIGNATURE("FAIL","WAL1", HttpStatus.BAD_REQUEST, "Sai chữ ký"),

    // ===== BALANCE =====
    INSUFFICIENT_BALANCE("FAIL","WAL2", HttpStatus.BAD_REQUEST, "Số dư không đủ"),

    // ===== USER =====
    USER_NOT_FOUND("FAIL","WAL3", HttpStatus.NOT_FOUND, "Không tìm thấy user"),

    // ===== WALLET =====
    WALLET_NOT_FOUND("FAIL","WAL4", HttpStatus.NOT_FOUND, "Không tìm thấy ví"),
    WALLET_LOCKED("FAIL","WAL5", HttpStatus.FORBIDDEN, "Ví đang bị khóa"),

    // ===== TRANSACTION =====
    TRANSACTION_FAILED("FAIL","WAL6", HttpStatus.INTERNAL_SERVER_ERROR, "Giao dịch thất bại"),
    DUPLICATE_TRANSACTION("FAIL","WAL7", HttpStatus.CONFLICT, "Giao dịch bị trùng"),

    // ===== VALIDATION =====
    INVALID_AMOUNT("FAIL","WAL8", HttpStatus.BAD_REQUEST, "Số tiền không hợp lệ");

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
