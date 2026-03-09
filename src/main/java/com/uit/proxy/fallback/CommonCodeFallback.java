package com.uit.proxy.fallback;

import com.uit.proxy.CommonClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CommonCodeFallback implements FallbackFactory<CommonClientService> {

    @Override
    public CommonClientService create(Throwable cause) {
        return new CommonClientService() {
            @Override
            public String hello() {
                System.out.println("33333333333333333333");
                return null;
            }
        };
    }
}
