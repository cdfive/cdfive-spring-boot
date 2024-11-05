package com.cdfive.springboot.webmvc.ext;

import com.cdfive.springboot.message.producer.RequestLogProducer;
import com.cdfive.springboot.message.vo.RequestLogMessageVo;
import com.cdfive.springboot.properties.ApplicationProperties;
import com.cdfive.springboot.properties.ExtProperties;
import com.cdfive.springboot.trace.TraceIdGenerator;
import com.cdfive.springboot.util.CommonUtil;
import com.cdfive.springboot.util.JsonUtil;
import com.cdfive.springboot.util.RequestContextUtil;
import com.cdfive.springboot.util.SpringContextUtil;
import org.slf4j.MDC;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author cdfive
 */
public class ExtDispatcherServlet extends DispatcherServlet {

    private static final long serialVersionUID = 4727130039164229708L;

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequestContextUtil.setRequestAttrStartTime(System.currentTimeMillis());

        String traceId = request.getHeader(RequestContextUtil.REQUEST_ATTR_TRACE_ID);
        if (traceId == null) {
            TraceIdGenerator traceIdGenerator = SpringContextUtil.getBean(TraceIdGenerator.class);
            traceId = traceIdGenerator.genTraceId();
        }
        RequestContextUtil.setRequestAttrTraceId(traceId);
        MDC.put(RequestContextUtil.REQUEST_ATTR_TRACE_ID, traceId);

        try {
            super.doDispatch(request, response);
        } finally {
            try {
                Boolean invokeStart = RequestContextUtil.getRequestInvokeStart();
                if (invokeStart != null && invokeStart) {
                    sendMessageRequestLog();
                }
            } catch (Exception e) {
                logger.error("sendMessageRequestLog error,requestUri=" + request.getRequestURI(), e);
            }

            MDC.clear();
        }
    }

    public void sendMessageRequestLog() {
        ExtProperties extProperties = SpringContextUtil.getBean(ExtProperties.class);
        if (!extProperties.getSendMessageRequestLog()) {
            logger.debug("ExtProperties sendMessageRequestLog false");
            return;
        }

        List<String> exculudeUris = extProperties.getSendMessageRequestLogExculudeUris();
        HttpServletRequest request = RequestContextUtil.getRequest();
        String requestUri = request.getRequestURI();
        if (!CollectionUtils.isEmpty(exculudeUris) && exculudeUris.contains(requestUri)) {
            return;
        }

        RequestLogProducer producer = null;
        try {
            producer = SpringContextUtil.getBean(RequestLogProducer.class);
        } catch (Exception e) {
            logger.debug("RequestLogProducer not found", e);
            return;
        }

        RequestLogMessageVo messageVo = new RequestLogMessageVo();
        messageVo.setTraceId(RequestContextUtil.getRequestAttrTraceId());

        ApplicationProperties applicationProperties = SpringContextUtil.getBean(ApplicationProperties.class);
        messageVo.setAppName(applicationProperties.getAppName());
        messageVo.setAppIp(applicationProperties.getAppIp());
        messageVo.setAppPort(applicationProperties.getAppPort());

        messageVo.setRequestUri(requestUri);
        messageVo.setRemoteAddr(request.getRemoteAddr());

        messageVo.setRequestBody(JsonUtil.controllerArgsToStr(RequestContextUtil.getRequestAttrReq()));
        messageVo.setResponseBody(JsonUtil.controllerArgsToStr(RequestContextUtil.getRequestAttrResp()));

        messageVo.setTimeCostMs(RequestContextUtil.getRequestAttrTimeCostMs());

        Exception exception = RequestContextUtil.getRequestAttrException();
        if (exception == null) {
            messageVo.setExExist(false);
        } else {
            messageVo.setExExist(true);
            messageVo.setExClassName(exception.getClass().getName());
            messageVo.setExMessage(exception.getMessage());
            messageVo.setExStackTrace(CommonUtil.getStackTraceAsString(exception));
        }

        messageVo.setStartTime(RequestContextUtil.getRequestAttrStartTime());

        producer.send(messageVo);
    }
}
