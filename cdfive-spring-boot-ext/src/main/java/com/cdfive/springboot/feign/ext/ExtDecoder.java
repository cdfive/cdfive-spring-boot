package com.cdfive.springboot.feign.ext;

import com.cdfive.springboot.common.JsonResult;
import com.cdfive.springboot.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.springframework.core.ParameterizedTypeReference;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author cdfive
 */
public class ExtDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        if (response == null) {
            return JsonResult.error("response empty");
        }

        String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));

//        return JsonUtil.strToObj(bodyStr, JsonResult.class);

//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readValue(bodyStr, objectMapper.constructType(type));

//        JsonUtil.strToObj(bodyStr, JsonResult.class, Object.class);

        Class<?> clazz = (Class<?>) type;
//        ParameterizedTypeReference<JsonResult<clazz>> typeReference = new ParameterizedTypeReference<JsonResult>() {
//        };

//        JsonResult<?> jsonResult = JsonUtil.strToObj(bodyStr, JsonResult.class, clazz.getClass());
        JsonResult<?> jsonResult = JsonUtil.strToObj(bodyStr, JsonResult.class, clazz);
        return jsonResult.getData();
    }
}
