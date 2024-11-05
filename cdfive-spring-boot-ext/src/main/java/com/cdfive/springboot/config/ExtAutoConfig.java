package com.cdfive.springboot.config;

import com.cdfive.springboot.heartbeat.HeartBeatSender;
import com.cdfive.springboot.heartbeat.HeartBeatTask;
import com.cdfive.springboot.properties.ApplicationProperties;
import com.cdfive.springboot.properties.ExtProperties;
import com.cdfive.springboot.trace.DefaultTraceIdGenerator;
import com.cdfive.springboot.trace.TraceIdGenerator;
import com.cdfive.springboot.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author cdfive
 * @date 2024-06-28
 */
@Slf4j
@Configuration
@ComponentScan({"com.cdfive.springboot"})
@AutoConfigureBefore({WebMvcAutoConfiguration.class, DispatcherServletAutoConfiguration.class})
@ConditionalOnProperty(name = "cdfive.springboot.enable", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(ExtProperties.class)
public class ExtAutoConfig {

    @PostConstruct
    public void init() {
        log.info("cdfive-spring-boot-ext enable");
    }

    @ConditionalOnMissingBean(SpringContextUtil.class)
    @Bean
    public SpringContextUtil springContextUtil() {
        return new SpringContextUtil();
    }

    @ConditionalOnMissingBean(TraceIdGenerator.class)
    @Bean
    public TraceIdGenerator traceIdGenerator() {
        return new DefaultTraceIdGenerator();
    }

    @Bean
    public HeartBeatSender heartBeatSender(ExtProperties extProperties, ApplicationProperties applicationProperties) {
        return new HeartBeatSender(extProperties.getApplicationAdminUrl(), applicationProperties);
    }

    @Bean
    public HeartBeatTask heartBeatTask(HeartBeatSender heartBeatSender) {
        return new HeartBeatTask(heartBeatSender);
    }
}
