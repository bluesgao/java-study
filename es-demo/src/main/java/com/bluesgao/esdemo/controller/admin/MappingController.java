package com.bluesgao.esdemo.controller.admin;

import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @ClassName：MappingController
 * @Description：
 * @Author：bluesgao
 * @Date：2021/1/26 10:13
 **/
@RestController()
@RequestMapping("/mapping")
public class MappingController {

    @Autowired
    private RestHighLevelClient client;

    /**
     * 获取index的mapping信息
     * @param index
     * @return
     */
    @GetMapping("/{index}")
    public Object getmapping(@PathVariable String index) {
        GetMappingsRequest request = new GetMappingsRequest();
        request.indices(index);
        GetMappingsResponse getMappingResponse = null;
        try {
            getMappingResponse = client.indices().getMapping(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getMappingResponse.toString();
    }
}

