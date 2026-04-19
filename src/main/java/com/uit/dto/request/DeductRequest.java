package com.uit.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeductRequest {
    private String userId;
    private BigDecimal amount;
}
