package com.uit.dto.request;

import com.uit.common.constant.ServiceTypeEnums;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InfoTransactionReq {

    @NotNull(message = "Amount không được null")
    @Min(value = 100, message = "Amount tối thiểu là 100")
    private double amount;

    @NotBlank(message = "Content không được để trống")
    @Size(max = 255, message = "Content tối đa 255 ký tự")
    private final String content;

    @NotBlank(message = "Phone không được để trống")
    private String phone;

//    @NotBlank(message = "RefCode không được để trống")
//    @Size(max = 50, message = "RefCode tối đa 50 ký tự")
    private String refCode;

    @NotBlank(message = "UserId không được để trống")
    private String userId;

    @NotNull(message = "ServiceType không được null")
    private ServiceTypeEnums serviceType;

    private ServiceTypeEnums packageType;
}
