package com.cdfive.springboot.admin.controller;

import com.cdfive.springboot.admin.service.InstanceService;
import com.cdfive.springboot.admin.vo.ReceiveHeartBeatReqVo;
import com.cdfive.springboot.admin.vo.ReceiveHeartBeatRespVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cdfive
 */
@RequiredArgsConstructor
@RequestMapping("/csba/application")
@RestController
public class ApplicationController {

    private final InstanceService instanceService;

    @PostMapping("/heartBeat")
    public ReceiveHeartBeatRespVo heartBeat(@RequestBody ReceiveHeartBeatReqVo reqVo) {
        return instanceService.receiveHeartBeat(reqVo);
    }
}
