package com.bluesgao.esdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.bluesgao.esdemo.common.PageBean;
import com.bluesgao.esdemo.common.PageResult;
import com.bluesgao.esdemo.common.ResultCode;
import com.bluesgao.esdemo.entity.search.*;
import com.bluesgao.esdemo.service.EsSearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @ClassName：EsSearchServiceImpl
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/1 09:53
 **/
@Slf4j
@Service
public class EsSearchServiceImpl implements EsSearchService {

    @Resource
    private RestHighLevelClient client;

    private String checkParam(EsSearchDto param) {
        StringBuilder errMsg = new StringBuilder();
        if (null == param) {
            errMsg.append("入参为空;");
        } else if (StringUtils.isBlank(param.getIndexName())) {
            errMsg.append("索引名为空;");
        } else if (param.getPageSize() > 1000) {
            errMsg.append("pageSize不能大于1000;");
        } else if (param.getPageNo() < 1) {
            param.setPageNo(1);
        } else if (param.getPageSize() <= 0) {
            param.setPageSize(10);
        } else if (param.getPageNo() * param.getPageSize() > 10000) {
            errMsg.append("搜索总数量不能大于10000");
        }
        return errMsg.toString();
    }

    private BoolQueryBuilder genNestedQueryBuilder(String path, NestedDto nestedDto) {
        //按照path字段组装dsl
        BoolQueryBuilder nestedBoolQuery = boolQuery();

        //match
        for (Map.Entry<String, String> e : nestedDto.getMatchMap().entrySet()) {
            //path.field
            String k = path + "." + e.getKey();
            nestedBoolQuery.must(termQuery(k, e.getValue()));
        }

        //filter
        for (Map.Entry<String, List<String>> e : nestedDto.getFilterMap().entrySet()) {
            //path.field
            String k = path + "." + e.getKey();
            List<String> valueList = e.getValue();
            nestedBoolQuery.filter(termsQuery(k, valueList.toArray()));
        }

        //mustNot
        for (Map.Entry<String, List<String>> e : nestedDto.getMustNotMap().entrySet()) {
            //path.field
            String k = path + "." + e.getKey();
            List<String> valueList = e.getValue();
            nestedBoolQuery.filter(termsQuery(k, valueList.toArray()));
        }

        //range
        for (Map.Entry<String, RangeDto> e : nestedDto.getRangeMap().entrySet()) {
            //path.field
            String k = path + "." + e.getKey();
            RangeDto rangeDto = e.getValue();
            if (null == e.getKey() || null == rangeDto) {
                continue;
            }

            if (null != rangeDto.getMin() || null != rangeDto.getMinStr()) {
                nestedBoolQuery.filter(rangeQuery(e.getKey()).gt(null == rangeDto.getMin() ? rangeDto.getMinStr() : rangeDto.getMin()));
            } else if (null != rangeDto.getMinE() || null != rangeDto.getMinEStr()) {
                nestedBoolQuery.filter(rangeQuery(e.getKey()).gte(null == rangeDto.getMinE() ? rangeDto.getMinEStr() : rangeDto.getMinE()));
            }

            if (null != rangeDto.getMax() || null != rangeDto.getMaxStr()) {
                nestedBoolQuery.filter(rangeQuery(e.getKey()).lt(null == rangeDto.getMax() ? rangeDto.getMaxStr() : rangeDto.getMax()));
            } else if (null != rangeDto.getMaxE() || null != rangeDto.getMaxEStr()) {
                nestedBoolQuery.filter(rangeQuery(e.getKey()).lte(null == rangeDto.getMaxE() ? rangeDto.getMaxEStr() : rangeDto.getMaxE()));
            }
        }
        return nestedBoolQuery;
    }

    private BoolQueryBuilder genQueryBuilder(EsSearchDto param) {
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        //ids
        if (null != param.getIds() && param.getIds().length > 0) {
            IdsQueryBuilder idsQueryBuilder = idsQuery();
            idsQueryBuilder.addIds(param.getIds());
            boolBuilder.must(idsQueryBuilder);
        }

        //match
        if (null != param.getMatchMap()) {
            for (Map.Entry<String, String> entry : param.getMatchMap().entrySet()) {
                boolBuilder.must(matchQuery(entry.getKey(), entry.getValue()));
            }
        }

        //filter
        if (null != param.getFilterMap()) {
            Map<String, List<String>> filterMap = param.getFilterMap();
            for (Map.Entry<String, List<String>> entry : filterMap.entrySet()) {
                List<String> valueList = entry.getValue();
                if (null != entry.getKey() && !CollectionUtils.isEmpty(valueList) && valueList.size() == 1) {
                    boolBuilder.filter(termQuery(entry.getKey(), valueList.get(0)));
                } else if (null != entry.getKey() && !CollectionUtils.isEmpty(valueList)) {
                    Object[] objects = valueList.toArray();
                    boolBuilder.filter(termsQuery(entry.getKey(), objects));
                }
            }
        }

        //mustNot
        if (null != param.getMustNotMap()) {
            Map<String, List<String>> ignoreMap = param.getMustNotMap();
            for (Map.Entry<String, List<String>> entry : ignoreMap.entrySet()) {
                List<String> valueList = entry.getValue();
                if (null != entry.getKey() && !CollectionUtils.isEmpty(valueList) && valueList.size() == 1) {
                    boolBuilder.mustNot(termQuery(entry.getKey(), valueList.get(0)));
                } else if (null != entry.getKey() && !CollectionUtils.isEmpty(valueList)) {
                    Object[] objects = valueList.toArray();
                    boolBuilder.mustNot(termsQuery(entry.getKey(), objects));
                }
            }
        }

        //range
        if (null != param.getRangeMap()) {
            Map<String, RangeDto> rangeMap = param.getRangeMap();
            for (Map.Entry<String, RangeDto> entry : rangeMap.entrySet()) {
                RangeDto rangeDTO = entry.getValue();
                if (null == entry.getKey() || null == rangeDTO) {
                    continue;
                }

                if (null != rangeDTO.getMin() || null != rangeDTO.getMinStr()) {
                    boolBuilder.filter(rangeQuery(entry.getKey()).gt(null == rangeDTO.getMin() ? rangeDTO.getMinStr() : rangeDTO.getMin()));
                } else if (null != rangeDTO.getMinE() || null != rangeDTO.getMinEStr()) {
                    boolBuilder.filter(rangeQuery(entry.getKey()).gte(null == rangeDTO.getMinE() ? rangeDTO.getMinEStr() : rangeDTO.getMinE()));
                }

                if (null != rangeDTO.getMax() || null != rangeDTO.getMaxStr()) {
                    boolBuilder.filter(rangeQuery(entry.getKey()).lt(null == rangeDTO.getMax() ? rangeDTO.getMaxStr() : rangeDTO.getMax()));
                } else if (null != rangeDTO.getMaxE() || null != rangeDTO.getMaxEStr()) {
                    boolBuilder.filter(rangeQuery(entry.getKey()).lte(null == rangeDTO.getMaxE() ? rangeDTO.getMaxEStr() : rangeDTO.getMaxE()));
                }
            }
        }

        //nested todo range
        if (null != param.getNestedMap()) {
            /*Map<String, Map<String, List<String>>> nestedMap = param.getNestedMap();
            for (Map.Entry<String, Map<String, List<String>>> entry : nestedMap.entrySet()) {
                String path = entry.getKey();
                Map<String, List<String>> value = entry.getValue();
                if (null != path && value != null && value.size() > 0) {
                    BoolQueryBuilder nestedBoolQuery = boolQuery();
                    for (Map.Entry<String, List<String>> e : value.entrySet()) {
                        //path.field
                        String k = path + "." + e.getKey();
                        List<String> v = e.getValue();
                        nestedBoolQuery.must(termsQuery(k, v.toArray()));
                    }
                    boolBuilder.must(nestedQuery(path, nestedBoolQuery, ScoreMode.None));
                }
            }*/

            for (Map.Entry<String, NestedDto> entry : param.getNestedMap().entrySet()) {
                boolBuilder.must(genNestedQueryBuilder(entry.getKey(), entry.getValue()));
            }
        }

        return boolBuilder;
    }

    private SearchRequest buildSearchRequest(EsSearchDto param) {


        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("fields.entity_id", "319");//这里可以根据字段进行搜索，must表示符合条件的，相反的mustnot表示不符合条件的
        // RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("fields_timestamp"); //新建range条件
        // rangeQueryBuilder.gte("2019-03-21T08:24:37.873Z"); //开始时间
        // rangeQueryBuilder.lte("2019-03-21T08:24:37.873Z"); //结束时间
        // boolBuilder.must(rangeQueryBuilder);
        //boolBuilder.must(matchQueryBuilder);

        //设置查询，可以是任何类型的QueryBuilder
        sourceBuilder.query(genQueryBuilder(param));


        //pageno和pagesize转为form,size
        int from = (param.getPageNo() - 1) * param.getPageSize();
        int size = param.getPageSize();
        //设置确定结果要从哪个索引开始搜索的from选项，默认为0
        sourceBuilder.from(from);
        //设置确定搜素命中返回数的size选项，默认为10
        sourceBuilder.size(size);
        //设置一个可选的超时，控制允许搜索的时间。
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //SearchSourceBuilder的源过滤
        sourceBuilder.fetchSource(param.getIncludeFields(), param.getExcludeFields());
        //排序
        for (SortDto sortDto : param.getSortList()) {
            SortOrder sortorder = SortOrder.DESC;
            if (sortDto.getSortEnum().getCode().equals(SortEnum.ASC)) {
                sortorder = SortOrder.ASC;
            }
            sourceBuilder.sort(sortDto.getColumn(), sortorder);
        }
        //索引
        SearchRequest searchRequest = new SearchRequest(param.getIndexName());
        searchRequest.source(sourceBuilder);

        return searchRequest;
    }

    @Override
    public PageResult<List<SearchResultDto>> commonSearch(EsSearchDto param) {
        Instant start = Instant.now();
        // 校验参数
        String errMsg = checkParam(param);
        if (null != errMsg && errMsg.length() > 0) {
            return PageResult.fail(ResultCode.PARAM_ERROR.getCode(), ResultCode.PARAM_ERROR.getMessage());
        }

        //构造搜索请求
        SearchRequest searchRequest = buildSearchRequest(param);
        log.info("构造搜索请求 searchRequest:{}", searchRequest.source());
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("common search error:{}", e);
        }
        if (searchResponse == null) {
            PageResult.fail(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMessage() + "search search error");
        }

        //处理搜索结果
        //SearchHits提供有关所有匹配的全局信息，例如总命中数或最高分数
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();

        //设置搜索数据
        List<SearchResultDto> dataList = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            log.info("search -> {}", hit.getSourceAsString());
            SearchResultDto dto = new SearchResultDto();
            dto.setDataMap(hit.getSourceAsMap());
            dto.setIdValue(hit.getId());//设置docid
            dataList.add(dto);
        }

        //设置分页参数
        PageBean pb = new PageBean(param.getPageNo(), param.getPageSize());
        pb.setTotalCount(hits.getTotalHits().value);

        //日志
        long duration = Duration.between(start, Instant.now()).toMillis();
        log.info("EsSearchService.commonSearch metric param[{}],totalCount[{}]，returnCount[{}],rtTime[{}]ms,searchTime[{}]ms",
                JSON.toJSONString(param), hits.getTotalHits().value, dataList.size(), duration, searchResponse.getTook().getMillis());

        return PageResult.success(dataList, pb);
    }
}
