package com.uit.dto.response;

import lombok.Data;

@Data
public class ValidClientRes {

    private boolean valid;
    private String username;
    private int expirationAccessToken;
}
