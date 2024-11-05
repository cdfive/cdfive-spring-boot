package com.cdfive.springboot.base;

import com.cdfive.springboot.util.SpringContextUtil;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author cdfive
 */
public abstract class AbstractService {

    protected void publishEvent(ApplicationEvent event) {
        SpringContextUtil.publishEvent(event);
    }

    protected void publishEvent(Object event) {
        SpringContextUtil.publishEvent(event);
    }

    protected void checkCondition(boolean condition, String msg) {
        if (!condition) {
            fail(msg);
        }
    }

    protected void checkNotNull(Object obj, String msg) {
        if (obj == null) {
            fail(msg);
        }
    }

    protected void checkNotBlank(String str, String msg) {
        if (StringUtils.isEmpty(str)) {
            fail(msg);
        }
    }

    protected void checkNotEmpty(Object obj, String msg) {
        if (ObjectUtils.isEmpty(obj)) {
            fail(msg);
        }
    }

    protected boolean isEmpty(Object obj) {
        return ObjectUtils.isEmpty(obj);
    }

    protected boolean isNotEmpty(Object obj) {
        return !ObjectUtils.isEmpty(obj);
    }

    protected Date now() {
        return new Date();
    }

    protected void fail(String msg) {
        throw exception(msg);
    }

    protected abstract ServiceException exception(String msg);
}
