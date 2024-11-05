package com.cdfive.springboot.admin.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cdfive
 */
@Data
public class ReceiveHeartBeatReqVo implements Serializable {

    private static final long serialVersionUID = 426565645328952365L;

    private String appName;

    private String appIp;

    private Integer appPort;
}
