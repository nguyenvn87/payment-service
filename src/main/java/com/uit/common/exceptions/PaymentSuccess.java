package com.uit.common.exceptions;

public class PaymentSuccess {

    private final String error;
    private final Object data;

    public PaymentSuccess(String error, Object data) {
        this.error = "SUCCESS";
        this.data = data;
    }
}
