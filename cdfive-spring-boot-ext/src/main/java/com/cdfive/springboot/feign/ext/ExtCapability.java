package com.cdfive.springboot.feign.ext;

import com.cdfive.springboot.feign.config.ExtFeignClientProperties;
import feign.Capability;
import feign.InvocationHandlerFactory;

/**
 * @author cdfive
 */
public class ExtCapability implements Capability {

    private final ExtFeignClientProperties extFeignClientProperties;

    public ExtCapability(ExtFeignClientProperties extFeignClientProperties) {
        this.extFeignClientProperties = extFeignClientProperties;
    }

    @Override
    public InvocationHandlerFactory enrich(InvocationHandlerFactory invocationHandlerFactory) {
        return new ExtInvocationHandlerFactory(invocationHandlerFactory, extFeignClientProperties);
    }
}
