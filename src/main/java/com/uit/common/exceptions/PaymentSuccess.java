package com.uit.common.exceptions;

import lombok.Data;

@Data
public class PaymentSuccess {

    private final String result;
    private final Object data;

    public PaymentSuccess(Object data) {
        this.result = "SUCCESS";
        this.data = data;
    }
}
