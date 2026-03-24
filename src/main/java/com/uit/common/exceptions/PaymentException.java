package com.uit.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class PaymentException extends RuntimeException{

    private String error;
    private String errorCode;
    private String errorStatusCode;
    private String errorMessage;

    public PaymentException(PaymentError paymentError) {
        this.error = paymentError.getResult();
        this.errorCode = paymentError.getErrorCode();
        this.errorStatusCode = paymentError.getErrorStatusCode();
        this.errorMessage = paymentError.getErrorMessage();
    }
}
