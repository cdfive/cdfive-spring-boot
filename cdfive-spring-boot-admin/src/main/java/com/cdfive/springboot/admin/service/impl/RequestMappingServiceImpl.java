package com.cdfive.springboot.admin.service.impl;

import com.cdfive.es.query.SearchQuery;
import com.cdfive.es.vo.EsEntityVo;
import com.cdfive.springboot.admin.model.es.RequestMapping;
import com.cdfive.springboot.admin.repository.es.RequestMappingRepository;
import com.cdfive.springboot.admin.service.RequestMappingService;
import com.cdfive.springboot.admin.vo.PageQueryRequestMappingListReqVo;
import com.cdfive.springboot.admin.vo.PageQueryRequestMappingListRespVo;
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

import java.util.function.Function;

/**
 * @author cdfive
 */
@RequiredArgsConstructor
@Service
public class RequestMappingServiceImpl implements RequestMappingService {

    private final RequestMappingRepository requestMappingRepository;

    @Override
    public PageRespVo<PageQueryRequestMappingListRespVo> pageQueryRequestMappingList(PageQueryRequestMappingListReqVo reqVo) {
        BoolQueryBuilder rootQuery = QueryBuilders.boolQuery();

        String appName = reqVo.getAppName();
        if (StringUtils.hasText(appName)) {
            rootQuery.filter(QueryBuilders.termQuery("appName", appName));
        }

        String mappingUri = reqVo.getMappingUri();
        if (StringUtils.hasText(mappingUri)) {
            rootQuery.filter(QueryBuilders.termQuery("mappingUri", mappingUri));
        }

        SearchQuery searchQuery = new SearchQuery();
        searchQuery.withQuery(rootQuery);
        searchQuery.withPageable(PageRequest.of(reqVo.getPageNum() - 1, reqVo.getPageSize()));
        searchQuery.withSort(SortBuilders.fieldSort("updateTime").order(SortOrder.DESC));

        Page<EsEntityVo<RequestMapping>> page = requestMappingRepository.search(searchQuery);
        PageRespVo<PageQueryRequestMappingListRespVo> pageRespVo = PageUtil.buildPage(page, new Function<EsEntityVo<RequestMapping>, PageQueryRequestMappingListRespVo>() {
            @Override
            public PageQueryRequestMappingListRespVo apply(EsEntityVo<RequestMapping> esEntityVo) {
                PageQueryRequestMappingListRespVo respVo = new PageQueryRequestMappingListRespVo();

                respVo.setId(esEntityVo.getId());

                RequestMapping entity = esEntityVo.getEntity();
                respVo.setAppName(entity.getAppName());
                respVo.setMappingUri(entity.getMappingUri());
                respVo.setHttpMethod(entity.getHttpMethod());
                respVo.setUpdateTime(entity.getUpdateTime());
                return respVo;
            }
        });
        return pageRespVo;
    }
}
