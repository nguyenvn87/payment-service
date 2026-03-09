package com.uit.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpStatusResponse {

    private String errorCode;
    private Integer httpStatus;
    private Object content;

    public HttpStatusResponse(String code, Object content) {
        this.errorCode = code;
        this.httpStatus = 400;
        this.content = content;
    }
}