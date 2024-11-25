package com.cdfive.springboot.feign.config;

import com.cdfive.springboot.feign.ext.ExtDecoder;
import com.cdfive.springboot.feign.ext.ExtFeign;
import com.cdfive.springboot.properties.ExtProperties;
import feign.Client;
import feign.Feign;
import feign.codec.Decoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author cdfive
 */
@ConditionalOnProperty(value = "ext.feign.client.enabled", havingValue = "true")
@Configuration
@EnableConfigurationProperties({ExtFeignClientProperties.class})
public class ExtFeignConfig {

    @Autowired
    private ExtFeignClientProperties extFeignClientProperties;

    @Bean
    public Feign.Builder extFeignBuilder() {
        return ExtFeign.builder(extFeignClientProperties);
    }

    @Bean
    public Decoder extDecoder() {
        return new ExtDecoder();
    }

//    @Bean
//    public Client feignClient(CachingSpringLoadBalancerFactory cachingFactory,
//                              SpringClientFactory clientFactory) {
//        return new LoadBalancerFeignClient(new ExtClient(null, null), cachingFactory,
//                clientFactory);
//    }
}
