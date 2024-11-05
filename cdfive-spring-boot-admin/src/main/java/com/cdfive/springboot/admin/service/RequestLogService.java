package com.cdfive.springboot.admin.service;

import com.cdfive.springboot.admin.vo.PageQueryRequestLogListReqVo;
import com.cdfive.springboot.admin.vo.PageQueryRequestLogListRespVo;
import com.cdfive.springboot.admin.vo.QueryRequestLogDetailReqVo;
import com.cdfive.springboot.admin.vo.QueryRequestLogDetailRespVo;
import com.cdfive.springboot.common.PageRespVo;

/**
 * @author cdfive
 */
public interface RequestLogService {

    PageRespVo<PageQueryRequestLogListRespVo> pageQueryRequestLogList(PageQueryRequestLogListReqVo reqVo);

    QueryRequestLogDetailRespVo queryRequestLogDetail(QueryRequestLogDetailReqVo reqVo);
}
