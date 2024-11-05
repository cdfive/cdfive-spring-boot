package com.cdfive.springboot.admin.controller;

import com.cdfive.springboot.admin.service.InstanceService;
import com.cdfive.springboot.admin.vo.PageQueryInstanceListReqVo;
import com.cdfive.springboot.admin.vo.PageQueryInstanceListRespVo;
import com.cdfive.springboot.common.PageRespVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cdfive
 */
@RequiredArgsConstructor
@RequestMapping("/csba/instance")
@RestController
public class InstanceController {

    private final InstanceService instanceService;

    @PostMapping("/pageList")
    public PageRespVo<PageQueryInstanceListRespVo> pageList(@RequestBody PageQueryInstanceListReqVo reqVo) {
        return instanceService.pageQueryInstanceList(reqVo);
    }

    @GetMapping("/nameList")
    public List<String> nameList() {
        return instanceService.queryInstanceNameList();
    }

    @GetMapping("/ipList")
    public List<String> appIpList(String appName) {
        return instanceService.queryInstanceIpList(appName);
    }
}
