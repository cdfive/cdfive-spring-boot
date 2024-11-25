package com.cdfive.springboot.webmvc.advice;

import com.cdfive.springboot.common.JsonResult;
import com.cdfive.springboot.util.JsonUtil;
import com.cdfive.springboot.util.RequestContextUtil;
import com.cdfive.springboot.webmvc.ext.ExtJsonMappingException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cdfive
 */
@Slf4j
@ConditionalOnClass(HttpServletRequest.class)
@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public JsonResult<String> exceptionHandler(HttpServletRequest request, Exception ex) {
        log.error("controller error,uri={},cost={}ms,reqVo={},respVo={}"
                , request.getRequestURI()
                , RequestContextUtil.getRequestAttrTimeCostMs()
                , JsonUtil.controllerArgsToStr(RequestContextUtil.getRequestAttrReq())
                , JsonUtil.controllerArgsToStr(RequestContextUtil.getRequestAttrResp())
                , ex);

//        HashMap<String, Object> map = new HashMap<>(4);
//        map.put("ts", System.currentTimeMillis());
//        map.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        map.put("msg", ex != null ? ex.getMessage() : null);
//        map.put("traceId", RequestContextUtil.getRequestAttrTraceId());
//        return map;

        String msg = parseMsg(ex);
        JsonResult<String> jsonResult = JsonResult.error(msg);
        jsonResult.setTraceId(RequestContextUtil.getRequestAttrTraceId());
        return jsonResult;
    }

    private String parseMsg(Exception ex) {
        if (ex == null) {
            return null;
        }

        if (ex instanceof HttpMessageNotReadableException) {
            HttpMessageNotReadableException hmnrEx = (HttpMessageNotReadableException) ex;
            Throwable hmnrExCause = hmnrEx.getCause();
            if (hmnrExCause != null) {
                if (hmnrExCause instanceof ExtJsonMappingException) {
                    ExtJsonMappingException ejmEx = (ExtJsonMappingException) hmnrExCause;
                    return ejmEx.getMessage();
                }
            }
        }

        if (ex instanceof HystrixRuntimeException) {
            HystrixRuntimeException hex = (HystrixRuntimeException) ex;
            Throwable hexCause = hex.getCause();
            if (hexCause != null) {
                return hexCause.getMessage();
            }
        }

        return ex.getMessage();
    }
}
