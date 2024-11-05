package com.cdfive.springboot.admin.message.consumer;

import com.cdfive.es.query.DeleteByQuery;
import com.cdfive.es.query.SearchQuery;
import com.cdfive.es.vo.EsEntityVo;
import com.cdfive.springboot.admin.model.es.Instance;
import com.cdfive.springboot.admin.model.es.RequestMapping;
import com.cdfive.springboot.admin.repository.es.InstanceRepository;
import com.cdfive.springboot.admin.repository.es.RequestMappingRepository;
import com.cdfive.springboot.message.vo.ApplicationStartedMessageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cdfive
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ApplicationStartedConsumer {

    private final InstanceRepository instanceRepository;

    private final RequestMappingRepository requestMappingRepository;

    @JmsListener(destination = "applicationStartedQueue", concurrency = "1")
    public void recevive(ApplicationStartedMessageVo messageVo) {
        String appName = messageVo.getAppName();
        String appIp = messageVo.getAppIp();
        Integer appPort = messageVo.getAppPort();

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.filter(QueryBuilders.termQuery("appName", appName));
        boolQuery.filter(QueryBuilders.termQuery("appIp", appIp));
        boolQuery.filter(QueryBuilders.termQuery("appPort", appPort));
        SearchQuery searchQuery = new SearchQuery();
        searchQuery.withQuery(boolQuery);
        Page<EsEntityVo<Instance>> page = instanceRepository.search(searchQuery);
        String id = null;
        List<EsEntityVo<Instance>> content = page.getContent();
        if (!CollectionUtils.isEmpty(content)) {
            id = content.get(0).getId();
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("startingTime", format(messageVo.getStartingTime()));
            params.put("startedTime", format(messageVo.getStartedTime()));
            params.put("startTimeCostMs", messageVo.getStartTimeCostMs());
            params.put("updateTime", format(System.currentTimeMillis()));
            instanceRepository.update(id, params);
        } else {
            Instance entity = new Instance();
            entity.setAppName(appName);
            entity.setAppIp(appIp);
            entity.setAppPort(appPort);

            entity.setStartingTime(convert(messageVo.getStartingTime()));
            entity.setStartedTime(convert(messageVo.getStartedTime()));
            entity.setStartTimeCostMs(messageVo.getStartTimeCostMs());
            entity.setUpdateTime(LocalDateTime.now());
            instanceRepository.save(entity);
        }

        BoolQueryBuilder deleteQuery = QueryBuilders.boolQuery();
        deleteQuery.filter(QueryBuilders.termQuery("appName", appName));
        DeleteByQuery deleteByQuery = new DeleteByQuery();
        deleteByQuery.withQuery(deleteQuery);
        requestMappingRepository.deleteByQuery(deleteByQuery);

        List<ApplicationStartedMessageVo.RequestMappingVo> requestMappingVos = messageVo.getRequestMappings();
        if (CollectionUtils.isEmpty(requestMappingVos)) {
            return;
        }

        List<RequestMapping> requestMappings = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (ApplicationStartedMessageVo.RequestMappingVo requestMappingVo : requestMappingVos) {
            RequestMapping requestMapping = new RequestMapping();
            requestMappings.add(requestMapping);

            requestMapping.setAppName(appName);

            String mappingUri = requestMappingVo.getMappingUri();
            requestMapping.setMappingUri(mappingUri);

            String httpMethod = requestMappingVo.getHttpMethod();
            requestMapping.setHttpMethod(httpMethod);

            requestMapping.setUpdateTime(now);
        }
        requestMappingRepository.save(requestMappings);
    }

    private LocalDateTime convert(Long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    private String format(Long timestamp) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(convert(timestamp));
    }
}
