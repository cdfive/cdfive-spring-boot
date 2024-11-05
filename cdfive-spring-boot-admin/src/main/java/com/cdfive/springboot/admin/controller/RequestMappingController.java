package com.cdfive.springboot.admin.controller;

import com.cdfive.springboot.admin.service.RequestMappingService;
import com.cdfive.springboot.admin.vo.PageQueryRequestMappingListReqVo;
import com.cdfive.springboot.admin.vo.PageQueryRequestMappingListRespVo;
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
@RequestMapping("/csba/requestMapping")
@RestController
public class RequestMappingController {

    private final RequestMappingService requestMappingService;

    @PostMapping("/pageList")
    public PageRespVo<PageQueryRequestMappingListRespVo> pageList(@RequestBody PageQueryRequestMappingListReqVo reqVo) {
        return requestMappingService.pageQueryRequestMappingList(reqVo);
    }
}
