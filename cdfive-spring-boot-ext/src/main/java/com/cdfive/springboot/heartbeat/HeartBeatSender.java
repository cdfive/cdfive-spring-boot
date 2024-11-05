package com.cdfive.springboot.heartbeat;

import com.cdfive.springboot.properties.ApplicationProperties;
import com.cdfive.springboot.util.JsonUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author cdfive
 */
@Slf4j
public class HeartBeatSender {

    private static final String HEART_BEAT_API = "/csba/application/heartBeat";

    @Getter
    private String applicationAdminUrl;

    private final ApplicationProperties applicationProperties;

    private final RestTemplate restTemplate;

    public HeartBeatSender(String applicationAdminUrl, ApplicationProperties applicationProperties) {
        if (!StringUtils.isEmpty(applicationAdminUrl)) {
            if (applicationAdminUrl.length() > 1 && applicationAdminUrl.endsWith("/")) {
                applicationAdminUrl = applicationAdminUrl.substring(0, applicationAdminUrl.length() - 1);
            }

            if (applicationAdminUrl.startsWith("http://")) {
                this.applicationAdminUrl = applicationAdminUrl + HEART_BEAT_API;
            } else {
                this.applicationAdminUrl = "http://" + applicationAdminUrl + HEART_BEAT_API;
            }
        } else {
            this.applicationAdminUrl = null;
        }

        this.applicationProperties = applicationProperties;
        this.restTemplate = new RestTemplate();
    }

    public boolean sendHeartBeat() {
        if (StringUtils.isEmpty(applicationAdminUrl)) {
            log.debug("appAdminUrl empty");
            return false;
        }

        Map<String, Object> paramMap = new LinkedHashMap<>(4);
        paramMap.put("appName", applicationProperties.getAppName());
        paramMap.put("appIp", applicationProperties.getAppIp());
        paramMap.put("appPort", applicationProperties.getAppPort());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> httpEntity = new HttpEntity<>(JsonUtil.objToStr(paramMap), httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(applicationAdminUrl, HttpMethod.POST, httpEntity, String.class);

        if (log.isDebugEnabled()) {
            log.debug("sendHeartBeat,statusCode={},body={}", responseEntity.getStatusCode(), responseEntity.getBody());
        }
        return true;
    }
}
