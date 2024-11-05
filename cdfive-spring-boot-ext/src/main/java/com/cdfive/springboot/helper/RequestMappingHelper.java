package com.cdfive.springboot.helper;

import com.cdfive.springboot.message.vo.ApplicationStartedMessageVo;
import com.cdfive.springboot.util.SpringContextUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
@ConditionalOnClass(RequestMappingHandlerMapping.class)
@Component
public class RequestMappingHelper {

    public List<ApplicationStartedMessageVo.RequestMappingVo> getRequestMappings() {
        List<ApplicationStartedMessageVo.RequestMappingVo> requestMappingVos = new ArrayList<>();

        RequestMappingHandlerMapping requestMappingHandlerMapping = SpringContextUtil.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        handlerMethods.forEach((mappingInfo, handlerMethod) -> {
            ApplicationStartedMessageVo.RequestMappingVo requestMappingVo = new ApplicationStartedMessageVo.RequestMappingVo();

            String mappingUri;
            Set<String> patterns = mappingInfo.getPatternsCondition().getPatterns();
            if (patterns.size() == 1) {
                mappingUri = patterns.iterator().next().toString();
            } else {
                mappingUri = patterns.stream().map(o -> o.toString()).collect(Collectors.joining(","));
            }
            if (mappingUri.equals("/error")) {
                return;
            }

            String httpMethod;
            Set<RequestMethod> methods = mappingInfo.getMethodsCondition().getMethods();
            if (CollectionUtils.isEmpty(methods)) {
                httpMethod = "ALL";
            } else if (methods.size() == 1) {
                httpMethod = methods.iterator().next().toString();
            } else {
                httpMethod = methods.stream().map(o -> o.toString()).collect(Collectors.joining(","));
            }
            requestMappingVo.setHttpMethod(httpMethod);

            requestMappingVo.setMappingUri(mappingUri);

            requestMappingVos.add(requestMappingVo);
        });

        return requestMappingVos;
    }
}
