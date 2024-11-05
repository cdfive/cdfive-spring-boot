package com.cdfive.springboot.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cdfive
 */
@Data
public class PageQueryInstanceListRespVo implements Serializable {

    private static final long serialVersionUID = 1025221268037623410L;

    private String appName;

    private String appIp;

    private Integer appPort;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime startingTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime startedTime;

    private Long startTimeCostMs;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime heartBeatTime;

    private Boolean healthy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime updateTime;
}
