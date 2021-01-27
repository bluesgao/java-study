package com.bluesgao.esdemo.controller.admin;

import com.alibaba.fastjson.JSON;
import com.bluesgao.esdemo.common.CommonResult;
import com.bluesgao.esdemo.entity.EsWriteDto;
import com.bluesgao.esdemo.service.EsWriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName：NestedDocController
 * @Description：
 * @Author：bluesgao
 * @Date：2021/1/26 16:51
 **/
@Slf4j
@RestController
@RequestMapping("/nested")
public class NestedDocController {
    @Resource
    private EsWriteService esWriteService;

    @GetMapping("/save")
    public CommonResult save() {
        EsWriteDto esWriteDto = EsWriteDto.builder().indexName("test_order")
                .docIdKey("id")
                .docIdValue("3")
                .idempotentKey("timestamp")
                .build();
        Map<String, Object> data = new HashMap<>(8);
        data.put("id", 3);
        data.put("timestamp", 1611658998L);
        data.put("userName", "gx333");
        data.put("orderStatus", 2);
        data.put("userId", "00003");
        esWriteDto.setData(data);

        CommonResult result = esWriteService.save(esWriteDto);
        log.info(JSON.toJSONString(result));
        return result;
    }
}
