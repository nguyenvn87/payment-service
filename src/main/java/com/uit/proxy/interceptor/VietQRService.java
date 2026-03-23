package com.uit.proxy.interceptor;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "VietQRService", url = "https://api.example.com")
public interface VietQRService {
}
