package com.uit.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "server.client")
public class ServerClientProperties {

    private Bive bive;
    private Podcast podcast;

    @Data
    public static class Bive {
        private String url;
        private List<Integer> pack; // tránh dùng tên "package"
    }

    @Data
    public static class Podcast {
        private String url;
    }

    @PostConstruct
    public void init() {
        if (bive != null && bive.getPack() != null) {
            log.info("Server client Bive url loaded: {}", bive.getUrl());
            log.info("Server client Bive packages loaded: {}", bive.getPack());
        }if (podcast != null) {
            log.info("Server client Podcast url loaded: {}", podcast.getUrl());
        }else {
            log.warn("Bive packages is empty or not configured!");
        }
    }
}