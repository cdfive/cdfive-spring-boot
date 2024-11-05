package com.cdfive.springboot.webmvc.ext;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author cdfive
 */
@ConditionalOnClass(WebMvcConfigurer.class)
@Component
public class ExtWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.stream().filter(o -> o instanceof MappingJackson2HttpMessageConverter).findFirst()
                .ifPresent(c -> {
                    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = (MappingJackson2HttpMessageConverter) c;
                    ObjectMapper objectMapper = mappingJackson2HttpMessageConverter.getObjectMapper();
                    objectMapper.addHandler(new ExtDeserializationProblemHandler());
                });
    }
}
