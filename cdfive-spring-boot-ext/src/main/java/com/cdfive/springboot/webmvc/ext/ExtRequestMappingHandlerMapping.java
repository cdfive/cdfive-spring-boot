package com.cdfive.springboot.webmvc.ext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author cdfive
 */
public class ExtRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        super.registerHandlerMethod(handler, method, mapping);

        logger.info("Mapped " + mapping + " to " + handler + "#" + method.getName());
    }

    @Override
    protected void handlerMethodsInitialized(Map<RequestMappingInfo, HandlerMethod> handlerMethods) {
        super.handlerMethodsInitialized(handlerMethods);

        logger.info(handlerMethods.size() + " mappings in " + formatMappingName());
    }
}
