package com.cdfive.springboot.admin.controller;

import com.cdfive.springboot.admin.vo.DemoReqVo;
import com.cdfive.springboot.admin.vo.DemoRespVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cdfive
 */
@RequestMapping("/csba/demo")
@RestController
public class DemoController {

    @RequestMapping("/test")
    public DemoRespVo test(@RequestBody(required = false) DemoReqVo reqVo) {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            throw new RuntimeException("debug", e);
        }
        DemoRespVo respVo = new DemoRespVo();
        respVo.setData("demo");
        return respVo;
    }
}
