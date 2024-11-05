package com.cdfive.springboot.admin.model.es;

import com.cdfive.es.annotation.Document;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cdfive
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(index = "request_log")
public class RequestLog implements Serializable {

    private static final long serialVersionUID = 6982228451095366665L;

    private String traceId;

    private String appName;

    private String appIp;

    private Integer appPort;

    private String requestUri;

    private String remoteAddr;

    private String requestBody;

    private String responseBody;

    private Long timeCostMs;

    private Boolean success;

    private String exClassName;

    private String exMessage;

    private String exStackTrace;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createTime;
}
