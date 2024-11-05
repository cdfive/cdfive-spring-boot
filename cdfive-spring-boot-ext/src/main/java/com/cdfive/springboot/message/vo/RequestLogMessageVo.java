package com.cdfive.springboot.message.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author cdfive
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestLogMessageVo implements Serializable {

    private static final long serialVersionUID = 7205267179824451994L;

    private String traceId;

    private String appName;

    private String appIp;

    private Integer appPort;

    private String requestUri;

    private String remoteAddr;

    private String requestBody;

    private String responseBody;

    private Long startTime;

    private Long timeCostMs;

    private Boolean exExist;

    private String exClassName;

    private String exMessage;

    private String exStackTrace;
}
