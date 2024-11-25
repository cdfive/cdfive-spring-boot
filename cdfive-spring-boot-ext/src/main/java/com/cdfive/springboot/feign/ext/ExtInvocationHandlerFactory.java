package com.cdfive.springboot.feign.ext;

import com.cdfive.springboot.feign.config.ExtFeignClientProperties;
import feign.InvocationHandlerFactory;
import feign.MethodMetadata;
import feign.Request;
import feign.Target;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class ExtInvocationHandlerFactory implements InvocationHandlerFactory {

    private final InvocationHandlerFactory delegate;

    private final ExtFeignClientProperties extFeignClientProperties;

    public ExtInvocationHandlerFactory(InvocationHandlerFactory delegate, ExtFeignClientProperties extFeignClientProperties) {
        this.delegate = delegate;
        this.extFeignClientProperties = extFeignClientProperties;
    }

    @Override
    public InvocationHandler create(Target target, Map<Method, MethodHandler> dispatch) {
        Map<String, List<ExtFeignClientProperties.FeignClientConfig>> configMap = extFeignClientProperties.getConfig();
        if (CollectionUtils.isEmpty(configMap)) {
            return delegate.create(target, dispatch);
        }

        List<ExtFeignClientProperties.FeignClientConfig> targetConfigList = configMap.get(target.name());
        if (CollectionUtils.isEmpty(targetConfigList)) {
            return delegate.create(target, dispatch);
        }

        Map<String, ExtFeignClientProperties.FeignClientConfig> targetConfigMap = targetConfigList.stream().collect(Collectors.toMap(o -> o.getPath(), o -> o, (o, n) -> n));

        Collection<MethodHandler> methodHandlers = dispatch.values();
        for (MethodHandler methodHandler : methodHandlers) {
            Field fieldMetadata = ReflectionUtils.findField(methodHandler.getClass(), "metadata");
            ReflectionUtils.makeAccessible(fieldMetadata);
            Object objMetadata = ReflectionUtils.getField(fieldMetadata, methodHandler);

            MethodMetadata methodMetadata = (MethodMetadata) objMetadata;
            String path = methodMetadata.template().path();

            ExtFeignClientProperties.FeignClientConfig pathConfig = targetConfigMap.get(path);
            if (pathConfig == null) {
                continue;
            }

            Integer readTimeout = pathConfig.getReadTimeout();
            if (readTimeout <= 0) {
                continue;
            }

            Field fieldOptions = ReflectionUtils.findField(methodHandler.getClass(), "options");
            ReflectionUtils.makeAccessible(fieldOptions);
            Object objOptions = ReflectionUtils.getField(fieldOptions, methodHandler);

            Request.Options options = (Request.Options) objOptions;

            Request.Options newOptions = new Request.Options(options.connectTimeout(), options.connectTimeoutUnit()
                    , readTimeout, options.readTimeoutUnit(), options.isFollowRedirects());

            ReflectionUtils.setField(fieldOptions, methodHandler, newOptions);
        }

        return delegate.create(target, dispatch);
    }
}
