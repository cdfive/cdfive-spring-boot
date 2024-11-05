package com.cdfive.springboot.startup;

import com.cdfive.springboot.heartbeat.HeartBeatTask;
import com.cdfive.springboot.helper.RequestMappingHelper;
import com.cdfive.springboot.message.producer.ApplicationStartedProducer;
import com.cdfive.springboot.message.vo.ApplicationStartedMessageVo;
import com.cdfive.springboot.properties.ApplicationProperties;
import com.cdfive.springboot.properties.ExtProperties;
import com.cdfive.springboot.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.List;

/**
 * @author cdfive
 */
@Slf4j
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (SpringContextUtil.getApplicationContext() == null) {
            return;
        }

        if (!ApplicationStartupInfo.started()) {
            return;
        }

        ApplicationProperties applicationProperties = SpringContextUtil.getBean(ApplicationProperties.class);
        log.info("{} application startup success,cost={}ms", applicationProperties.getAppName(), ApplicationStartupInfo.getStartTimeCostMs());

        ExtProperties extProperties = SpringContextUtil.getBean(ExtProperties.class);
        log.debug("sendMessageApplicationStarted={}", extProperties.getSendMessageApplicationStarted());
        if (extProperties.getSendMessageApplicationStarted()) {
            try {
                log.debug("sendMessageApplicationStarted start");
                sendMessageApplicationStarted(applicationProperties);
                log.debug("sendMessageApplicationStarted success");
            } catch (Exception e) {
                log.error("sendMessageApplicationStarted error", e);
            }
        }

        HeartBeatTask heartBeatTask = SpringContextUtil.getBean(HeartBeatTask.class);
        heartBeatTask.start();
    }

    public void sendMessageApplicationStarted(ApplicationProperties properties) {
        ApplicationStartedProducer producer = null;
        try {
            producer = SpringContextUtil.getBean(ApplicationStartedProducer.class);
        } catch (Exception e) {
            log.debug("ApplicationStartedProducer not found", e);
            return;
        }

        ApplicationStartedMessageVo messageVo = new ApplicationStartedMessageVo();
        messageVo.setAppName(properties.getAppName());
        messageVo.setAppIp(properties.getAppIp());
        messageVo.setAppPort(properties.getAppPort());

        messageVo.setStartingTime(ApplicationStartupInfo.getStartingTime());
        messageVo.setStartedTime(ApplicationStartupInfo.getStartedTime());
        messageVo.setStartTimeCostMs(ApplicationStartupInfo.getStartTimeCostMs());

        if (SpringContextUtil.containsBean("requestMappingHelper")) {
            RequestMappingHelper requestMappingHelper = (RequestMappingHelper) SpringContextUtil.getBean("requestMappingHelper");
            if (requestMappingHelper != null) {
                List<ApplicationStartedMessageVo.RequestMappingVo> requestMappings = requestMappingHelper.getRequestMappings();
                messageVo.setRequestMappings(requestMappings);
            }
        }

        producer.send(messageVo);
    }
}
