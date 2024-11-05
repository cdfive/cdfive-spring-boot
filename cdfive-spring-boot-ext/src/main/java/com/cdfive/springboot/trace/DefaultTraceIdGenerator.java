package com.cdfive.springboot.trace;

import com.cdfive.springboot.util.CommonUtil;

import java.util.UUID;

/**
 * @author cdfive
 */
public class DefaultTraceIdGenerator implements TraceIdGenerator {

    @Override
    public String genTraceId() {
        return CommonUtil.getUUID();
    }
}
