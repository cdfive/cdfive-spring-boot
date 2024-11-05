package com.cdfive.springboot.admin.config;

import com.cdfive.springboot.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * @author cdfive
 */
@Slf4j
@RefreshScope
@Configuration
public class GlobalConfig {

    @PostConstruct
    public void init() {
        LinkedHashMap<String, Object> map = Arrays.stream(this.getClass().getSuperclass().getDeclaredFields())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .collect(LinkedHashMap::new, (m, f) -> {
                    ReflectionUtils.makeAccessible(f);
                    m.put(f.getName(), ReflectionUtils.getField(f, this));
                }, LinkedHashMap::putAll);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(map);
            log.info("GlobalConfig init,json={}", json);
        } catch (JsonProcessingException e) {
            log.error("GlobalConfig init error,json={}", JsonUtil.objToStr(map), e);
        }
    }

    @Value("${global.config.name1:}")
    private String name1;

    @Value("${global.config.name2:#{null}}")
    private String name2;

    @Value("${global.config.num1:}")
    private Integer num1;

    @Value("${global.config.num2:#{null}}")
    private Integer num2;

    @Value("${global.config.nums:#{null}}")
    private List<Integer> nums;

    @Value("${global.config.items:}")
    private Set<String> items;

    @Value("#{'${global.config.productCodes:111,222}'.split(',')}")
    private List<String> codes1;

    @Value("#{'${global.config.productCodes:#{null}}'.split(',')}")
    private List<String> codes2;
}
