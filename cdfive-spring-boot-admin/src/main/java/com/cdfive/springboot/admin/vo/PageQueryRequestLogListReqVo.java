package com.cdfive.springboot.admin.vo;

import com.cdfive.springboot.common.PageReqVo;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author cdfive
 */
@Data
public class PageQueryRequestLogListReqVo extends PageReqVo {

    private static final long serialVersionUID = -2912067829601913802L;

    private String id;

    private String traceId;

    private String appName;

    private String appIp;

    private Integer appPort;

    private String requestUri;

    private String requestBody;

    private String responseBody;

    private Long timeCostMsMin;

    private Long timeCostMsMax;

    private Boolean success;

    private LocalDateTime createTimeStart;

    private LocalDateTime createTimeEnd;
}
