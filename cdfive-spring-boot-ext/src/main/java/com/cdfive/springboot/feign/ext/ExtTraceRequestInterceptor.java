package com.cdfive.springboot.feign.ext;

import com.cdfive.springboot.util.RequestContextUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * @author cdfive
 */
@Slf4j
@ConditionalOnClass(RequestInterceptor.class)
@Component
public class ExtTraceRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String traceId = RequestContextUtil.getRequestAttrTraceId();
        if (traceId == null) {
            return;
        }

        template.header(RequestContextUtil.REQUEST_ATTR_TRACE_ID, traceId);
    }
}
