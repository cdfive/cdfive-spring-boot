
package com.cdfive.springboot.admin.message.consumer;

import com.cdfive.springboot.admin.model.es.RequestLog;
import com.cdfive.springboot.admin.repository.es.RequestLogRepository;
import com.cdfive.springboot.message.vo.RequestLogMessageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author cdfive
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class RequestLogConsumer {

    private final RequestLogRepository requestLogRepository;

    @JmsListener(destination = "requestLogQueue")
    public void recevive(RequestLogMessageVo messageVo) {
        RequestLog entity = new RequestLog();
        entity.setTraceId(messageVo.getTraceId());
        entity.setAppName(messageVo.getAppName());
        entity.setAppIp(messageVo.getAppIp());
        entity.setAppPort(messageVo.getAppPort());
        entity.setRequestUri(messageVo.getRequestUri());
        entity.setRemoteAddr(messageVo.getRemoteAddr());
        entity.setRequestBody(messageVo.getRequestBody());
//        entity.setResponseBody(messageVo.getResponseBody());
        entity.setTimeCostMs(messageVo.getTimeCostMs());
        entity.setSuccess(messageVo.getExExist() != null && !messageVo.getExExist());
        entity.setExClassName(messageVo.getExClassName());
        entity.setExMessage(messageVo.getExMessage());
        entity.setExStackTrace(messageVo.getExStackTrace());

        Long startTime = messageVo.getStartTime();
        if (startTime != null) {
            entity.setStartTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(startTime), ZoneId.systemDefault()));
        }

        entity.setCreateTime(LocalDateTime.now());
        requestLogRepository.save(entity);
    }
}
