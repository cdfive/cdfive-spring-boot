package com.cdfive.springboot.feign.ext;

import com.cdfive.springboot.feign.config.ExtFeignClientProperties;
import feign.Feign;

/**
 * @author cdfive
 */
public class ExtFeign {

    public static Builder builder(ExtFeignClientProperties extFeignClientProperties) {
        return new Builder(extFeignClientProperties);
    }

    public static final class Builder extends Feign.Builder {

        private final ExtFeignClientProperties extFeignClientProperties;

        public Builder(ExtFeignClientProperties extFeignClientProperties) {
            this.extFeignClientProperties = extFeignClientProperties;
        }

        @Override
        public Feign build() {
            //  !!!Important: java.lang.StackOverflowError
//            super.addCapability(new ExtCapability()).build();
            super.addCapability(new ExtCapability(extFeignClientProperties));
            return super.build();
        }
    }
}
