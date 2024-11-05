package com.cdfive.springboot.admin.vo;

import com.cdfive.springboot.common.PageReqVo;
import lombok.Data;

/**
 * @author cdfive
 */
@Data
public class PageQueryInstanceListReqVo extends PageReqVo {

    private static final long serialVersionUID = -1255997258795016304L;

    private String appName;

    private String appIp;

    private Integer appPort;
}
