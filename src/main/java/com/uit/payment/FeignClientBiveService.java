package com.uit.payment;

import com.uit.dto.request.DataSyncBankReq;
import com.uit.dto.response.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "FeignClientBiveService", url = "${server.client.bive.url}")
public interface FeignClientBiveService {

    @PutMapping(value ="/api/bank/sync/data", consumes = "application/json")
    ResponseEntity<TokenResponse> syneDataToService(@RequestBody DataSyncBankReq dataSyncBankReq);
}
