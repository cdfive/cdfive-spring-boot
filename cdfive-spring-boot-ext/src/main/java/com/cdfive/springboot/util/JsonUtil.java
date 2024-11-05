package com.cdfive.springboot.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.registerModule(new Jdk8Module());
        OBJECT_MAPPER.registerModule(new ParameterNamesModule());
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public static String objToStr(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T strToObj(String str, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(str, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({"rawtypes"})
    public static <T> T strToObj(String str, Class<T> parametrizedClass, Class... parameterClasses) {
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(parametrizedClass, parameterClasses);
        try {
            return OBJECT_MAPPER.readValue(str, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String controllerArgsToStr(Object args) {
        if (args == null) {
            return null;
        }

        if (args instanceof String) {
            return (String) args;
        }

        if (args instanceof HttpServletRequest || args instanceof HttpServletResponse) {
            return null;
        }

        if (args instanceof Object[]) {
            Object[] reqObjs = (Object[]) args;
            if (reqObjs.length == 0) {
                return null;
            }

            List<Object> reqList = Arrays.stream(reqObjs).collect(Collectors.toList());
            reqList.removeIf(o -> o instanceof HttpServletRequest || o instanceof HttpServletResponse);
            return JsonUtil.objToStr(reqList);
        }

        return JsonUtil.objToStr(args);
    }
}
