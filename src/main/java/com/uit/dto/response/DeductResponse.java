package com.uit.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class DeductResponse {
    private String userId;
    private BigDecimal remainBalance;
    private String status;
}
