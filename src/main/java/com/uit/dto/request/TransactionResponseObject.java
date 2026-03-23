package com.uit.dto.request;

import lombok.Data;

@Data
public class TransactionResponseObject {

    private String reftransactionid;

    public TransactionResponseObject(String reftransactionid) {
        this.reftransactionid = reftransactionid;
    }

    // Getters and Setters
    // ...
}