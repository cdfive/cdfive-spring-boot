package com.cdfive.springboot.feign.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cdfive
 */
@Data
@ConfigurationProperties(prefix = "ext.feign.client")
public class ExtFeignClientProperties {

    // !!!Important: yml map /xx/yy=>xxyy
//    private Map<String, Map<String, FeignClientConfig>> config = new HashMap<>();
    private Map<String, List<FeignClientConfig>> config = new HashMap<>();

    @Data
    public static class FeignClientConfig {

        private String path;

        private Integer readTimeout;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Integer getReadTimeout() {
            return readTimeout;
        }

        public void setReadTimeout(Integer readTimeout) {
            this.readTimeout = readTimeout;
        }
    }

    public Map<String, List<FeignClientConfig>> getConfig() {
        return config;
    }

    public void setConfig(Map<String, List<FeignClientConfig>> config) {
        this.config = config;
    }
}
