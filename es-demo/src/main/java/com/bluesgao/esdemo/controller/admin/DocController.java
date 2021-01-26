package com.bluesgao.esdemo.controller.admin;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @ClassName：DocController
 * @Description：
 * @Author：bluesgao
 * @Date：2021/1/26 14:09
 **/
@RestController
@RequestMapping("/doc")
public class DocController {
    @Resource
    private RestHighLevelClient client;

    @PostMapping("/insert/{index}/{docId}")
    public void insert(@PathVariable String index, @PathVariable String docId, @RequestBody Map<String, Object> data) {
        IndexRequest indexRequest = new IndexRequest(index).id(docId).source(data);
        IndexResponse indexResponse = null;
        try {
            indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("insertDoc:" + indexResponse.getId());
    }

    @GetMapping("/query/{index}/{docId}")
    public String query(@PathVariable String index, @PathVariable String docId) {
        GetRequest getRequest = new GetRequest(index, docId);
        GetResponse getResponse = null;
        try {
            getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("queryDoc:" + getResponse.getSourceAsString());
        return getResponse.getSourceAsString();
    }

    @PostMapping("/update/{index}/{docId}")
    public void update(@PathVariable String index, @PathVariable String docId, @RequestBody Map<String, Object> data) {
        UpdateRequest request = new UpdateRequest(index, docId).doc(data);
        UpdateResponse updateResponse = null;
        try {
            updateResponse = client.update(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("updateDoc:" + updateResponse.getId());
    }

    @GetMapping("/delete/{index}/{docId}")
    public boolean delete(@PathVariable String index, @PathVariable String docId) {
        DeleteRequest request = new DeleteRequest(index, docId);
        DeleteResponse deleteResponse = null;
        try {
            deleteResponse = client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (deleteResponse != null &&
                deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND) {
            System.out.println("文档删除成功");
            return true;
        }
        return false;
    }
}
