package com.cdfive.springboot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author cdfive
 */
@Data
@ConfigurationProperties(prefix = "cdfive.springboot")
public class ExtProperties {

    private Boolean enable;

    private String applicationAdminUrl;

    private Boolean sendMessageApplicationStarted;

    private Boolean sendMessageRequestLog;

    private List<String> sendMessageRequestLogExculudeUris;

    private Boolean wrapJsonResultResponse;

    public Boolean getSendMessageApplicationStarted() {
        if (sendMessageApplicationStarted == null) {
            return true;
        }

        return sendMessageApplicationStarted;
    }

    public Boolean getSendMessageRequestLog() {
        if (sendMessageRequestLog == null) {
            return true;
        }

        return sendMessageRequestLog;
    }

    public Boolean getWrapJsonResultResponse() {
        if (wrapJsonResultResponse == null) {
            return true;
        }

        return wrapJsonResultResponse;
    }
}
