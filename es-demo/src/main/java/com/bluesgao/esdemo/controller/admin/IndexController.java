package com.bluesgao.esdemo.controller.admin;


import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName：IndexController
 * @Description：
 * @Author：bluesgao
 * @Date：2021/1/26 10:57
 **/
@RestController
@RequestMapping("/index")
public class IndexController {

    @Resource
    RestHighLevelClient client;

    /**
     * 创建索引
     * @throws IOException
     */
    @PostMapping
    public void create() {
        CreateIndexRequest request = new CreateIndexRequest("person_index");//索引名不能有大写字母，必须全部小写，否则es无法创建索引
        // 分片配置
        request.settings(Settings.builder().put("index.number_of_shards", 1).put("index.number_of_replicas", 0));
        // mapping配置
        Map<String, Object> name = new HashMap<String, Object>();
        name.put("type", "text");
        Map<String, Object> age = new HashMap<String, Object>();
        age.put("type", "text");

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("name", name);
        properties.put("age", age);

        Map<String, Object> mapping = new HashMap<String, Object>();
        mapping.put("properties", properties);
        request.mapping(mapping);

        // 为索引设置一个别名
        request.alias(new Alias("person_alias"));

        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(request.index()+"创建索引"+createIndexResponse.isAcknowledged());
    }



    @GetMapping("/{index}")
    public Object getIndex(@PathVariable String index) {
        try {
            GetIndexRequest request = new GetIndexRequest(index);
            request.includeDefaults(true);
            request.humanReadable();
            GetIndexResponse getIndexResponse = client.indices().get(request, RequestOptions.DEFAULT);
            return getIndexResponse.getSettings();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     */
    @GetMapping("/exists/{index}")
    public Boolean exists(@PathVariable String index) {
        GetIndexRequest request = new GetIndexRequest(index);
        request.includeDefaults(true);
        request.indicesOptions(IndicesOptions.lenientExpandOpen());
        boolean exists = false;
        try {
            exists = client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (exists) {
            System.out.println("索引存在");
        } else {
            System.out.println("索引不存在");

        }
        return exists;
    }

    /**
     * 打开索引
     */
    @GetMapping("/open/{index}")
    public boolean open(@PathVariable String index) {
        OpenIndexRequest request = new OpenIndexRequest(index);
        boolean acknowledged = false;
        try {
            OpenIndexResponse openIndexResponse = client.indices().open(request, RequestOptions.DEFAULT);
            acknowledged = openIndexResponse.isAcknowledged();
            System.out.println("openIndex:" + acknowledged);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return acknowledged;
    }

    /***
     * 关闭索引
     */
    @GetMapping("/close/{index}")
    public boolean close(@PathVariable String index) {
        CloseIndexRequest request = new CloseIndexRequest(index);
        boolean acknowledged = false;
        try {
            AcknowledgedResponse closeIndexResponse = client.indices().close(request, RequestOptions.DEFAULT);
            acknowledged = closeIndexResponse.isAcknowledged();
            System.out.println("closeIndex:" + acknowledged);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return acknowledged;
    }

    /**
     * 删除索引
     */
    @GetMapping("/delete/{index}")
    public boolean delete(@PathVariable String index) {
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        boolean acknowledged = false;
        try {
            AcknowledgedResponse deleteIndexResponse = client.indices().delete(request, RequestOptions.DEFAULT);
            acknowledged = deleteIndexResponse.isAcknowledged();
            System.out.println("deleteIndex:" + acknowledged);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return acknowledged;
    }

}
