package com.uit.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataSyncBankReq {

    private String userId;
    private String pac;
    private double price;
}
