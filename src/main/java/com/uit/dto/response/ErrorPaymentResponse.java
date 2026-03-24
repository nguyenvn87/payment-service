package com.uit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorPaymentResponse {

    private String error;
    private String errorCode;
    private HttpStatus status;
    private String message;

}
