package com.uit.proxy.config;

import com.uit.common.exceptions.BusinessException;
import com.uit.utils.JsonUtils;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = FeignException.errorStatus(methodKey, response, 10, 1000);
        switch (response.status()){
            case 400:
            {
                BusinessException e = JsonUtils.convertJsonToObject(exception.contentUTF8(), BusinessException.class);
                return e;
            }

            case 404:
                return new BusinessException("Product not found");
            case 503:
                return new BusinessException("Service not found");
            default:
                return new BusinessException("Service not found");
        }
    }
}
