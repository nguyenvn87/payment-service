package com.uit.dto.response;

import lombok.Data;

@Data
public class ErrorResponse {
    private boolean error;
    private String errorReason;
    private String toastMessage;
    private Object object;

    public ErrorResponse(boolean error, String errorReason, String toastMessage, Object object) {
        this.error = error;
        this.errorReason = errorReason;
        this.toastMessage = toastMessage;
        this.object = object;
    }

    // Getters and Setters
    // ...
}
