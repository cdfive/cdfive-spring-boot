package com.cdfive.springboot.admin.service;

import com.cdfive.springboot.admin.vo.PageQueryRequestMappingListReqVo;
import com.cdfive.springboot.admin.vo.PageQueryRequestMappingListRespVo;
import com.cdfive.springboot.common.PageRespVo;

/**
 * @author cdfive
 */
public interface RequestMappingService {

    PageRespVo<PageQueryRequestMappingListRespVo> pageQueryRequestMappingList(PageQueryRequestMappingListReqVo reqVo);
}
