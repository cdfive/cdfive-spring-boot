package com.cdfive.springboot.webmvc.ext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;

import java.io.IOException;

/**
 * @author cdfive
 */
public class ExtDeserializationProblemHandler extends DeserializationProblemHandler {

    @Override
    public Object handleWeirdStringValue(DeserializationContext ctxt, Class<?> targetType, String valueToConvert, String failureMsg) throws IOException {
        JsonParser parser = ctxt.getParser();
        JsonStreamContext context = parser.getParsingContext();
        String currentName = context.getCurrentName();
        throw new ExtJsonMappingException("入参解析失败,字段:" + currentName + "(类型:" + targetType.getSimpleName() + ")格式不合法");
    }
}
