package com.cdfive.springboot.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cdfive
 */
@Slf4j
@ConditionalOnClass(EurekaClient.class)
@RequestMapping("/ext/instance")
@RestController
public class ExtInstanceController {

    @Autowired
    private EurekaClient eurekaClient;

    @GetMapping("/shutdown")
    public Boolean shutdown() {
        long start = System.currentTimeMillis();
        log.info("shutdown start");
        eurekaClient.shutdown();
        log.info("shutdown success,cost={}ms", (System.currentTimeMillis() - start));
        return true;
    }

    @GetMapping("/info")
    public InstanceInfo info() {
        return eurekaClient.getApplicationInfoManager().getInfo();
    }

    @GetMapping("/status")
    public InstanceInfo.InstanceStatus status() {
        return eurekaClient.getApplicationInfoManager().getInfo().getStatus();
    }
}
