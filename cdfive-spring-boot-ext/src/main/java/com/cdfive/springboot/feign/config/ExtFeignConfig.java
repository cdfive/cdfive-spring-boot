package com.cdfive.springboot.feign.config;

import com.cdfive.springboot.feign.ext.ExtFeign;
import com.cdfive.springboot.properties.ExtProperties;
import feign.Client;
import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cdfive
 */
@Configuration
@EnableConfigurationProperties({ExtFeignClientProperties.class})
public class ExtFeignConfig {

    @Autowired
    private ExtFeignClientProperties extFeignClientProperties;

    @Bean
    public Feign.Builder extFeignBuilder() {
        return ExtFeign.builder(extFeignClientProperties);
    }
}
