package com.uit.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReq {
    private String id;
    private String username;
    private String name;
    private String email;
    private String phoneNumber;
    private boolean isExist = false;

}
