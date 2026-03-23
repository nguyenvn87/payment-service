package com.uit.dto.response;

import com.uit.dto.request.TransactionResponseObject;

public class SuccessResponse {
    private boolean error;
    private String errorReason;
    private String toastMessage;
    private TransactionResponseObject object;

    public SuccessResponse(boolean error, String errorReason, String toastMessage, TransactionResponseObject object) {
        this.error = error;
        this.errorReason = errorReason;
        this.toastMessage = toastMessage;
        this.object = object;
    }

    // Getters and Setters
    // ...
}
