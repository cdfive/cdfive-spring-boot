package com.cdfive.springboot.webmvc.advice;

import com.cdfive.springboot.common.JsonResult;
import com.cdfive.springboot.properties.ExtProperties;
import com.cdfive.springboot.util.RequestContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author cdfive
 */
@ConditionalOnClass(ResponseBodyAdvice.class)
@RestControllerAdvice
public class JsonResultReponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private ExtProperties extProperties;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (!extProperties.getWrapJsonResultResponse()) {
            return false;
        }

        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (!extProperties.getWrapJsonResultResponse()) {
            return body;
        }

        if (body instanceof JsonResult) {
            ((JsonResult<Object>) body).setTraceId(RequestContextUtil.getRequestAttrTraceId());
            return body;
        }

        JsonResult<Object> jsonResult = JsonResult.success(body);
        jsonResult.setTraceId(RequestContextUtil.getRequestAttrTraceId());
        return jsonResult;
    }
}
