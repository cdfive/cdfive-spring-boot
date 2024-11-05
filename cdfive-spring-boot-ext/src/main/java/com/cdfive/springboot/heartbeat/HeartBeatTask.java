package com.cdfive.springboot.heartbeat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.util.StringUtils;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author cdfive
 */
@Slf4j
public class HeartBeatTask {

    private final ScheduledExecutorService executorService;

    private final HeartBeatSender sender;

    public HeartBeatTask(HeartBeatSender sender) {
        CustomizableThreadFactory threadFactory = new CustomizableThreadFactory("HeartBeatTask");
        threadFactory.setDaemon(true);
        this.executorService = new ScheduledThreadPoolExecutor(1, threadFactory, new ThreadPoolExecutor.DiscardOldestPolicy());
        this.sender = sender;
    }

    public void start() {
        String applicationAdminUrl = this.sender.getApplicationAdminUrl();
        if (StringUtils.isEmpty(applicationAdminUrl)) {
            log.debug("applicationAdminUrl empty");
            return;
        }

        this.executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean result = sender.sendHeartBeat();
                    if (result) {
                        log.debug("sendHeartBeat success");
                    } else {
                        log.warn("sendHeartBeat failed");
                    }
                } catch (Exception e) {
                    log.error("sendHeartBeat error", e);
                }
            }
        }, 5_000, 5_000, TimeUnit.MILLISECONDS);
    }
}
