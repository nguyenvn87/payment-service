package com.uit.payment;

import com.uit.dto.request.DataSyncBankReq;
import com.uit.dto.response.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "SyncDataService", url = "${server.client.bive.url}")
public interface FeignClientSyncDataService {

    @PutMapping(value ="/api/bank/sync/data", consumes = "application/json")
    ResponseEntity<TokenResponse> syneDataToService(@RequestBody DataSyncBankReq dataSyncBankReq);
}
