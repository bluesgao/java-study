package com.bluesgao.esdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.bluesgao.esdemo.common.PageBean;
import com.bluesgao.esdemo.common.PageResult;
import com.bluesgao.esdemo.common.ResultCode;
import com.bluesgao.esdemo.entity.search.*;
import com.bluesgao.esdemo.service.EsSearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
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
        if (!CollectionUtils.isEmpty(nestedDto.getMatchMap())) {
            for (Map.Entry<String, String> e : nestedDto.getMatchMap().entrySet()) {
                if (null == e.getKey() || null == e.getValue()) {
                    continue;
                }
                //path.field
                String k = path + "." + e.getKey();
                nestedBoolQuery.must(termQuery(k, e.getValue()));
            }
        }

        //filter
        if (!CollectionUtils.isEmpty(nestedDto.getFilterMap())) {
            for (Map.Entry<String, List<String>> e : nestedDto.getFilterMap().entrySet()) {
                if (null == e.getKey() || null == e.getValue()) {
                    continue;
                }
                //path.field
                String k = path + "." + e.getKey();
                List<String> valueList = e.getValue();
                nestedBoolQuery.filter(termsQuery(k, valueList.toArray()));
            }
        }

        //mustNot
        if (!CollectionUtils.isEmpty(nestedDto.getMustNotMap())) {
            for (Map.Entry<String, List<String>> e : nestedDto.getMustNotMap().entrySet()) {
                if (null == e.getKey() || null == e.getValue()) {
                    continue;
                }
                //path.field
                String k = path + "." + e.getKey();
                List<String> valueList = e.getValue();
                nestedBoolQuery.filter(termsQuery(k, valueList.toArray()));
            }
        }

        //range
        if (!CollectionUtils.isEmpty(nestedDto.getRangeMap())) {
            for (Map.Entry<String, RangeDto> e : nestedDto.getRangeMap().entrySet()) {
                if (null == e.getKey() || null == e.getValue()) {
                    continue;
                }
                //path.field
                String k = path + "." + e.getKey();
                RangeDto rangeDto = e.getValue();
                RangeQueryBuilder rangeQueryBuilder = rangeQuery(k);
                if (rangeDto.getFromDto() != null) {
                    rangeQueryBuilder.from(rangeDto.getFromDto().getFrom(), rangeDto.getFromDto().isIncludeUpper());
                }

                if (rangeDto.getToDto() != null) {
                    rangeQueryBuilder.to(rangeDto.getToDto().getTo(), rangeDto.getToDto().isIncludeLower());
                }
                nestedBoolQuery.must(rangeQueryBuilder);
                //nestedBoolQuery.must(rangeQuery(k).from(32).to(34));
            }
        }
        System.out.println(nestedBoolQuery.toString());
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
                if (null == entry.getKey() || null == entry.getValue()) {
                    continue;
                }
                boolBuilder.must(matchQuery(entry.getKey(), entry.getValue()));
            }
        }

        //filter
        if (null != param.getFilterMap()) {
            for (Map.Entry<String, List<String>> entry : param.getFilterMap().entrySet()) {
                if (null == entry.getKey() || null == entry.getValue()) {
                    continue;
                }
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
            for (Map.Entry<String, List<String>> entry : param.getMustNotMap().entrySet()) {
                if (null == entry.getKey() || null == entry.getValue()) {
                    continue;
                }
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
            for (Map.Entry<String, RangeDto> entry : param.getRangeMap().entrySet()) {
                if (null == entry.getKey() || null == entry.getValue()) {
                    continue;
                }

                String k = entry.getKey();
                RangeDto rangeDto = entry.getValue();
                RangeQueryBuilder rangeQueryBuilder = rangeQuery(k);
                if (rangeDto.getFromDto() != null) {
                    rangeQueryBuilder.from(rangeDto.getFromDto().getFrom(), rangeDto.getFromDto().isIncludeUpper());
                }

                if (rangeDto.getToDto() != null) {
                    rangeQueryBuilder.to(rangeDto.getToDto().getTo(), rangeDto.getToDto().isIncludeLower());
                }

                boolBuilder.filter(rangeQueryBuilder);
            }
        }

        //nested
        if (null != param.getNestedMap()) {
            for (Map.Entry<String, NestedDto> entry : param.getNestedMap().entrySet()) {
                boolBuilder.must(nestedQuery(entry.getKey(), genNestedQueryBuilder(entry.getKey(), entry.getValue()), ScoreMode.None));
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
        if (!CollectionUtils.isEmpty(param.getSortList())) {
            for (SortDto sortDto : param.getSortList()) {
                SortOrder sortorder = SortOrder.DESC;
                if (sortDto.getSortEnum().getCode().equals(SortEnum.ASC)) {
                    sortorder = SortOrder.ASC;
                }
                sourceBuilder.sort(sortDto.getColumn(), sortorder);
            }
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
