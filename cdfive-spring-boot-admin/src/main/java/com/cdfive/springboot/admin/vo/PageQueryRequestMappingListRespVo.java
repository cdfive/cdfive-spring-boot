package com.cdfive.springboot.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cdfive
 */
@Data
public class PageQueryRequestMappingListRespVo implements Serializable {

    private static final long serialVersionUID = 2056388415481627854L;

    private String id;

    private String appName;

    private String mappingUri;

    private String httpMethod;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime updateTime;
}
