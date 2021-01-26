package com.bluesgao.esdemo.controller.admin;

import com.bluesgao.esdemo.entity.EsWriteDto;
import com.bluesgao.esdemo.service.EsWriteService;
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
@RestController
@RequestMapping("/nested")
public class NestedDocController {
    @Resource
    private EsWriteService esWriteService;

    @GetMapping("/save")
    public boolean save() {
        EsWriteDto esWriteDto = EsWriteDto.builder().indexName("test_order")
                .docIdKey("id")
                .idempotentKey("timestamp")
                .build();
        Map<String, Object> data = new HashMap<>(8);
        data.put("id", 2);
        data.put("timestamp", 1611658993L);
        data.put("userName", "gx66");
        data.put("orderStatus", 66);
        esWriteDto.setData(data);

        esWriteService.save(esWriteDto);
        return true;
    }
}
