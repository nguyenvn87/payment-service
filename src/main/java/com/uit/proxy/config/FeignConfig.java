package com.uit.proxy.config;

import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class FeignConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.NONE;
    }

    @Bean
    ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}
