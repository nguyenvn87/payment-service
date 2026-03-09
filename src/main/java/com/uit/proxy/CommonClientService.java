package com.uit.proxy;

import com.uit.proxy.config.FeignConfig;
import com.uit.proxy.fallback.CommonCodeFallback;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(value = "common-code-service", fallbackFactory = CommonCodeFallback.class, configuration = FeignConfig.class)
@Headers("Authorization: {token}")
public interface CommonClientService {

    @RequestMapping(value = "/test/test-feign", method = GET)
    //String hello(@RequestHeader("Authorization") String token);
    String hello();
}
