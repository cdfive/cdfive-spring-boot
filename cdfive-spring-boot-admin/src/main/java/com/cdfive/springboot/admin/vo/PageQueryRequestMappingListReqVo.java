package com.cdfive.springboot.admin.vo;

import com.cdfive.springboot.common.PageReqVo;
import lombok.Data;

/**
 * @author cdfive
 */
@Data
public class PageQueryRequestMappingListReqVo extends PageReqVo {

    private static final long serialVersionUID = 93934352247128825L;

    private String appName;

    private String mappingUri;
}
