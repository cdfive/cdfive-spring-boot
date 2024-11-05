package com.cdfive.springboot.webmvc.ext;

import com.cdfive.springboot.util.RequestContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.lang.reflect.Method;

/**
 * @author cdfive
 */
@Slf4j
public class ExtServletInvocableHandlerMethod extends ServletInvocableHandlerMethod {

    public ExtServletInvocableHandlerMethod(Object handler, Method method) {
        super(handler, method);
    }

    public ExtServletInvocableHandlerMethod(HandlerMethod handlerMethod) {
        super(handlerMethod);
    }

    @Override
    protected Object doInvoke(Object... args) throws Exception {
        Object result = null;
        try {
            RequestContextUtil.setRequestAttrReq(args);
            RequestContextUtil.setRequestAttrInvokeStart(Boolean.TRUE);
            result = super.doInvoke(args);
            RequestContextUtil.setRequestAttrResp(result);
        } catch (Exception e) {
            RequestContextUtil.setRequestAttrException(e);
            throw e;
        } finally {
            RequestContextUtil.setRequestAttrTimeCostMs((System.currentTimeMillis() - RequestContextUtil.getRequestAttrStartTime()));
        }
        return result;
    }
}
