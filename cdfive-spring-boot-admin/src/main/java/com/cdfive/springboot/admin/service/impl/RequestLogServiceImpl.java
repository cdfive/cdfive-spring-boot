package com.cdfive.springboot.admin.service.impl;

import com.cdfive.es.query.SearchQuery;
import com.cdfive.es.vo.EsEntityVo;
import com.cdfive.springboot.admin.model.es.RequestLog;
import com.cdfive.springboot.admin.repository.es.RequestLogRepository;
import com.cdfive.springboot.admin.service.RequestLogService;
import com.cdfive.springboot.admin.vo.PageQueryRequestLogListReqVo;
import com.cdfive.springboot.admin.vo.PageQueryRequestLogListRespVo;
import com.cdfive.springboot.admin.vo.QueryRequestLogDetailReqVo;
import com.cdfive.springboot.admin.vo.QueryRequestLogDetailRespVo;
import com.cdfive.springboot.common.PageRespVo;
import com.cdfive.springboot.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.function.Function;

/**
 * @author cdfive
 */
@RequiredArgsConstructor
@Service
public class RequestLogServiceImpl implements RequestLogService {

    private final RequestLogRepository requestLogRepository;

    @Override
    public PageRespVo<PageQueryRequestLogListRespVo> pageQueryRequestLogList(PageQueryRequestLogListReqVo reqVo) {
        BoolQueryBuilder rootQuery = QueryBuilders.boolQuery();

        String id = reqVo.getId();
        if (StringUtils.hasText(id)) {
            rootQuery.filter(QueryBuilders.termQuery("_id", id));
        }

        String traceId = reqVo.getTraceId();
        if (StringUtils.hasText(traceId)) {
            rootQuery.filter(QueryBuilders.termQuery("traceId", traceId));
        }

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

        String requestUri = reqVo.getRequestUri();
        if (StringUtils.hasText(requestUri)) {
            rootQuery.filter(QueryBuilders.termQuery("requestUri", requestUri));
        }

        String requestBody = reqVo.getRequestBody();
        if (StringUtils.hasText(requestBody)) {
            rootQuery.filter(QueryBuilders.matchQuery("requestBody", requestBody));
        }

        String responseBody = reqVo.getResponseBody();
        if (StringUtils.hasText(responseBody)) {
            rootQuery.filter(QueryBuilders.matchQuery("responseBody", responseBody));
        }

        Long timeCostMsMin = reqVo.getTimeCostMsMin();
        if (timeCostMsMin != null) {
            rootQuery.filter(QueryBuilders.rangeQuery("timeCostMs").gte(timeCostMsMin));
        }

        Long timeCostMsMax = reqVo.getTimeCostMsMax();
        if (timeCostMsMax != null) {
            rootQuery.filter(QueryBuilders.rangeQuery("timeCostMs").lte(timeCostMsMax));
        }

        Boolean success = reqVo.getSuccess();
        if (success != null) {
            rootQuery.filter(QueryBuilders.termQuery("success", success));
        }

        LocalDateTime createTimeStart = reqVo.getCreateTimeStart();
        if (createTimeStart != null) {
            rootQuery.filter(QueryBuilders.rangeQuery("createTime").gte(createTimeStart));
        }

        LocalDateTime createTimeEnd = reqVo.getCreateTimeEnd();
        if (createTimeEnd != null) {
            rootQuery.filter(QueryBuilders.rangeQuery("createTime").lte(createTimeEnd));
        }

        SearchQuery searchQuery = new SearchQuery();
        searchQuery.withQuery(rootQuery);
        searchQuery.withPageable(PageRequest.of(reqVo.getPageNum() - 1, reqVo.getPageSize()));
        searchQuery.withSort(SortBuilders.fieldSort("startTime").order(SortOrder.DESC));
        searchQuery.trackTotalHits(true);

        Page<EsEntityVo<RequestLog>> page = requestLogRepository.search(searchQuery);
        PageRespVo<PageQueryRequestLogListRespVo> pageRespVo = PageUtil.buildPage(page, new Function<EsEntityVo<RequestLog>, PageQueryRequestLogListRespVo>() {
            @Override
            public PageQueryRequestLogListRespVo apply(EsEntityVo<RequestLog> esEntityVo) {
                PageQueryRequestLogListRespVo respVo = new PageQueryRequestLogListRespVo();
                
                respVo.setId(esEntityVo.getId());

                RequestLog entity = esEntityVo.getEntity();
                respVo.setTraceId(entity.getTraceId());
                respVo.setAppName(entity.getAppName());
                respVo.setAppIp(entity.getAppIp());
                respVo.setAppPort(entity.getAppPort());
                respVo.setRequestUri(entity.getRequestUri());
                respVo.setRemoteAddr(entity.getRemoteAddr());
                respVo.setTimeCostMs(entity.getTimeCostMs());
                respVo.setSuccess(entity.getSuccess());
                respVo.setExClassName(entity.getExClassName());
                respVo.setStartTime(entity.getStartTime());
                respVo.setCreateTime(entity.getCreateTime());
                return respVo;
            }
        });
        return pageRespVo;
    }

    @Override
    public QueryRequestLogDetailRespVo queryRequestLogDetail(QueryRequestLogDetailReqVo reqVo) {
        String id = reqVo.getId();

        EsEntityVo<RequestLog> esEntityVo = requestLogRepository.findOne(id);
        if (esEntityVo == null) {
            return null;
        }

        QueryRequestLogDetailRespVo respVo = new QueryRequestLogDetailRespVo();
        respVo.setId(esEntityVo.getId());

        RequestLog entity = esEntityVo.getEntity();
        respVo.setTraceId(entity.getTraceId());
        respVo.setAppName(entity.getAppName());
        respVo.setAppIp(entity.getAppIp());
        respVo.setAppPort(entity.getAppPort());
        respVo.setRequestUri(entity.getRequestUri());
        respVo.setRemoteAddr(entity.getRemoteAddr());
        respVo.setRequestBody(entity.getRequestBody());
        respVo.setResponseBody(entity.getResponseBody());
        respVo.setTimeCostMs(entity.getTimeCostMs());
        respVo.setSuccess(entity.getSuccess());
        respVo.setExClassName(entity.getExClassName());
        respVo.setExMessage(entity.getExMessage());
        respVo.setExStackTrace(entity.getExStackTrace());
        respVo.setStartTime(entity.getStartTime());
        respVo.setCreateTime(entity.getCreateTime());
        return respVo;
    }
}
