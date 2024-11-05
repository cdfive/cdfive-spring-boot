package com.cdfive.springboot.admin.service.impl;

import com.cdfive.es.query.SearchQuery;
import com.cdfive.es.vo.EsEntityVo;
import com.cdfive.springboot.admin.model.es.Instance;
import com.cdfive.springboot.admin.repository.es.InstanceRepository;
import com.cdfive.springboot.admin.service.InstanceService;
import com.cdfive.springboot.admin.vo.PageQueryInstanceListReqVo;
import com.cdfive.springboot.admin.vo.PageQueryInstanceListRespVo;
import com.cdfive.springboot.admin.vo.ReceiveHeartBeatReqVo;
import com.cdfive.springboot.admin.vo.ReceiveHeartBeatRespVo;
import com.cdfive.springboot.common.PageRespVo;
import com.cdfive.springboot.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
@RequiredArgsConstructor
@Service
public class InstanceServiceImpl implements InstanceService {

    private final InstanceRepository applicationRepository;

    @Override
    public ReceiveHeartBeatRespVo receiveHeartBeat(ReceiveHeartBeatReqVo reqVo) {
        ReceiveHeartBeatRespVo respVo = new ReceiveHeartBeatRespVo();

        String appName = reqVo.getAppName();
        String appIp = reqVo.getAppIp();
        Integer appPort = reqVo.getAppPort();

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.filter(QueryBuilders.termQuery("appName", appName));
        boolQuery.filter(QueryBuilders.termQuery("appIp", appIp));
        boolQuery.filter(QueryBuilders.termQuery("appPort", appPort));
        SearchQuery searchQuery = new SearchQuery();
        searchQuery.withQuery(boolQuery);
        Page<EsEntityVo<Instance>> page = applicationRepository.search(searchQuery);
        String id = null;
        List<EsEntityVo<Instance>> content = page.getContent();
        if (!CollectionUtils.isEmpty(content)) {
            id = content.get(0).getId();
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("heartBeatTime", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now()));
            params.put("updateTime", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now()));
            applicationRepository.update(id, params);

            respVo.setAdd(false);
            return respVo;
        }

        Instance entity = new Instance();
        entity.setAppName(appName);
        entity.setAppIp(appIp);
        entity.setAppPort(appPort);
        entity.setHeartBeatTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        applicationRepository.save(entity);

        respVo.setAdd(true);
        return respVo;
    }

    @Override
    public PageRespVo<PageQueryInstanceListRespVo> pageQueryInstanceList(PageQueryInstanceListReqVo reqVo) {
        BoolQueryBuilder rootQuery = QueryBuilders.boolQuery();

        String appName = reqVo.getAppName();
        if (StringUtils.hasText(appName)) {
            rootQuery.filter(QueryBuilders.termQuery("appName", appName));
        }

        String appIp = reqVo.getAppIp();
        if (StringUtils.hasText(appIp)) {
            rootQuery.filter(QueryBuilders.termQuery("appIp", appIp));
        }

        Integer appPort = reqVo.getAppPort();
        if (appPort != null) {
            rootQuery.filter(QueryBuilders.termQuery("appPort", appPort));
        }

        SearchQuery searchQuery = new SearchQuery();
        searchQuery.withQuery(rootQuery);
        searchQuery.withPageable(PageRequest.of(reqVo.getPageNum() - 1, reqVo.getPageSize()));
        searchQuery.withSort(SortBuilders.fieldSort("appName").order(SortOrder.ASC));

        LocalDateTime now = LocalDateTime.now();
        Page<EsEntityVo<Instance>> page = applicationRepository.search(searchQuery);
        PageRespVo<PageQueryInstanceListRespVo> pageRespVo = PageUtil.buildPage(page, new Function<EsEntityVo<Instance>, PageQueryInstanceListRespVo>() {
            @Override
            public PageQueryInstanceListRespVo apply(EsEntityVo<Instance> applicationEsEntityVo) {
                PageQueryInstanceListRespVo respVo = new PageQueryInstanceListRespVo();
                Instance entity = applicationEsEntityVo.getEntity();
                respVo.setAppName(entity.getAppName());
                respVo.setAppIp(entity.getAppIp());
                respVo.setAppPort(entity.getAppPort());
                respVo.setStartingTime(entity.getStartingTime());
                respVo.setStartedTime(entity.getStartedTime());
                respVo.setStartTimeCostMs(entity.getStartTimeCostMs());

                LocalDateTime heartBeatTime = entity.getHeartBeatTime();
                respVo.setHeartBeatTime(heartBeatTime);

                if (heartBeatTime != null && Math.abs(ChronoUnit.SECONDS.between(heartBeatTime, now)) <= 30) {
                    respVo.setHealthy(true);
                } else {
                    respVo.setHealthy(false);
                }

                respVo.setUpdateTime(entity.getUpdateTime());
                return respVo;
            }
        });
        return pageRespVo;
    }

    @Override
    public List<String> queryInstanceNameList() {
        MatchAllQueryBuilder rootQuery = QueryBuilders.matchAllQuery();
        SearchQuery searchQuery = new SearchQuery();
        searchQuery.withQuery(rootQuery);
        searchQuery.withPageable(PageRequest.of(0, 100));
        searchQuery.withSort(SortBuilders.fieldSort("appName").order(SortOrder.ASC));
        Page<EsEntityVo<Instance>> page = applicationRepository.search(searchQuery);
        List<String> appNames = page.getContent().stream().map(o -> o.getEntity().getAppName()).distinct().collect(Collectors.toList());
        return appNames;
    }

    @Override
    public List<String> queryInstanceIpList(String appName) {
        BoolQueryBuilder rootQuery = QueryBuilders.boolQuery();
        rootQuery.filter(QueryBuilders.termQuery("appName", appName));
        SearchQuery searchQuery = new SearchQuery();
        searchQuery.withQuery(rootQuery);
        searchQuery.withPageable(PageRequest.of(0, 100));
        Page<EsEntityVo<Instance>> page = applicationRepository.search(searchQuery);
        List<String> appIps = page.getContent().stream().map(o -> o.getEntity().getAppIp()).collect(Collectors.toList());
        return appIps;
    }
}
