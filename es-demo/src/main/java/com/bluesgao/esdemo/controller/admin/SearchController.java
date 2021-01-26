package com.bluesgao.esdemo.controller.admin;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName：SearchController
 * @Description：
 * @Author：bluesgao
 * @Date：2021/1/26 14:37
 **/
@RestController
@RequestMapping("/search")
public class SearchController {
    @Resource
    private RestHighLevelClient client;

    @GetMapping("/{index}")
    @ResponseBody
    public List<Map<String, Object>> queryAll(@PathVariable String index) {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Map<String, Object>> resultList = new ArrayList<>();

        if (searchResponse == null || searchResponse.getHits() == null ||
                searchResponse.getHits().getHits() == null ||
                searchResponse.getHits().getHits().length == 0) {
            return resultList;
        }

        System.out.println(searchResponse.getHits());
        SearchHits hits = searchResponse.getHits();

        for (SearchHit hit : hits.getHits()) {
            // do something with the SearchHit
            //String sourceAsString = hit.getSourceAsString();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //String documentTitle = (String) sourceAsMap.get("title");
            //List<Object> users = (List<Object>) sourceAsMap.get("user");
            //Map<String, Object> innerObject = (Map<String, Object>) sourceAsMap.get("innerObject");
            resultList.add(sourceAsMap);
        }

        return resultList;
    }
}
