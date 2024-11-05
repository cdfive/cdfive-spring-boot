package com.cdfive.springboot.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cdfive
 */
@Data
public class PageQueryRequestLogListRespVo implements Serializable {

    private static final long serialVersionUID = 7863314472135363114L;

    private String id;

    private String traceId;

    private String appName;

    private String appIp;

    private Integer appPort;

    private String requestUri;

    private String remoteAddr;

    private Long timeCostMs;

    private Boolean success;

    private String exClassName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createTime;
}
