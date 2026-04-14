package com.uit.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

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
}