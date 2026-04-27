package com.uit.payment;

import com.uit.dto.request.DataSyncBankReq;
import com.uit.dto.response.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "FeignClientPodcastService", url = "${server.client.podcast.url}")
public interface FeignClientPodcastService {

    @PostMapping(value ="/api/bank/sync/data", consumes = "application/json")
    ResponseEntity<TokenResponse> syneDataToService(@RequestBody DataSyncBankReq dataSyncBankReq);
}
