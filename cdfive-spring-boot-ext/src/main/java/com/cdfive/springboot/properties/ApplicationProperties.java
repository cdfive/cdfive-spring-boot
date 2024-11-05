package com.cdfive.springboot.properties;

import com.cdfive.springboot.util.HostNameUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @author cdfive
 */
@Slf4j
@Data
@Configuration
public class ApplicationProperties implements InitializingBean {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.ip:#{null}}")
    private String appIp;

    @Value("${server.port:#{null}}")
    private Integer appPort;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!StringUtils.hasText(appIp)) {
            appIp = HostNameUtil.getIp();
        }

        if (appPort == null) {
            appPort = 8080;
        }

        log.debug("appName={},appIp={},appPort={}", appName, appIp, appPort);
    }
}
