package com.cdfive.springboot.admin.controller;

import com.cdfive.springboot.admin.service.RequestLogService;
import com.cdfive.springboot.admin.vo.PageQueryRequestLogListReqVo;
import com.cdfive.springboot.admin.vo.PageQueryRequestLogListRespVo;
import com.cdfive.springboot.admin.vo.QueryRequestLogDetailReqVo;
import com.cdfive.springboot.admin.vo.QueryRequestLogDetailRespVo;
import com.cdfive.springboot.common.PageRespVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cdfive
 */
@RequiredArgsConstructor
@RequestMapping("/csba/requestLog")
@RestController
public class RequestLogController {

    private final RequestLogService requestLogService;

    @PostMapping("/pageList")
    public PageRespVo<PageQueryRequestLogListRespVo> pageList(@RequestBody PageQueryRequestLogListReqVo reqVo) {
        return requestLogService.pageQueryRequestLogList(reqVo);
    }

    @PostMapping("/detail")
    public QueryRequestLogDetailRespVo detail(@RequestBody QueryRequestLogDetailReqVo reqVo) {
        return requestLogService.queryRequestLogDetail(reqVo);
    }
}
