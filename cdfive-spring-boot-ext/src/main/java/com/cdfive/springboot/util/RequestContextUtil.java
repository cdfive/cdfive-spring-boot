package com.cdfive.springboot.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cdfive
 */
public class RequestContextUtil {

    public static String REQUEST_ATTR_TRACE_ID = "_trace_id";

    public static String REQUEST_ATTR_START_TIME = "_start_time";

    public static String REQUEST_ATTR_INVOKE_START = "_invoke_start";

    public static String REQUEST_ATTR_REQ = "_req";

    public static String REQUEST_ATTR_RESP = "_resp";

    public static String REQUEST_ATTR_TIME_COST_MS = "_time_cost_ms";

    public static String REQUEST_ATTR_EXCEPTION = "_exception";

    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes == null) {
            return null;
        }

        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return null;
        }

        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static void setRequestAttrTraceId(String traceId) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.setAttribute(REQUEST_ATTR_TRACE_ID, traceId);
        }
    }

    public static String getRequestAttrTraceId() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }

        return (String) request.getAttribute(REQUEST_ATTR_TRACE_ID);
    }

    public static void setRequestAttrStartTime(Long startTime) {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return;
        }

        request.setAttribute(REQUEST_ATTR_START_TIME, startTime);
    }

    public static Long getRequestAttrStartTime() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }

        return (Long) request.getAttribute(REQUEST_ATTR_START_TIME);
    }

    public static void setRequestAttrInvokeStart(Boolean invokeStart) {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return;
        }

        request.setAttribute(REQUEST_ATTR_INVOKE_START, invokeStart);
    }

    public static Boolean getRequestInvokeStart() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return (Boolean) request.getAttribute(REQUEST_ATTR_INVOKE_START);
    }

    public static void setRequestAttrReq(Object req) {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return;
        }

        request.setAttribute(REQUEST_ATTR_REQ, req);
    }

    public static Object getRequestAttrReq() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }

        return request.getAttribute(REQUEST_ATTR_REQ);
    }

    public static void setRequestAttrResp(Object resp) {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return;
        }

        request.setAttribute(REQUEST_ATTR_RESP, resp);
    }

    public static Object getRequestAttrResp() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }

        return request.getAttribute(REQUEST_ATTR_RESP);
    }

    public static void setRequestAttrTimeCostMs(Long timeCostMs) {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return;
        }

        request.setAttribute(REQUEST_ATTR_TIME_COST_MS, timeCostMs);
    }

    public static Long getRequestAttrTimeCostMs() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }

        return (Long) request.getAttribute(REQUEST_ATTR_TIME_COST_MS);
    }

    public static void setRequestAttrException(Exception exception) {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return;
        }

        request.setAttribute(REQUEST_ATTR_EXCEPTION, exception);
    }

    public static Exception getRequestAttrException() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }

        return (Exception) request.getAttribute(REQUEST_ATTR_EXCEPTION);
    }
}
