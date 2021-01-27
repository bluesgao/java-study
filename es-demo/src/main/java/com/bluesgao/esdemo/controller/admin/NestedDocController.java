package com.bluesgao.esdemo.controller.admin;

import com.alibaba.fastjson.JSON;
import com.bluesgao.esdemo.common.CommonResult;
import com.bluesgao.esdemo.entity.EsCommonWriteDto;
import com.bluesgao.esdemo.entity.EsNestedWriteDto;
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

    @GetMapping("/commonSave")
    public CommonResult commonSave() {
        EsCommonWriteDto dto = EsCommonWriteDto.builder().build();
        dto.setIndexName("test_order");
        dto.setDocIdKey("id");
        dto.setDocIdValue("3");
        dto.setIdempotentKey("timestamp");
        dto.setIdempotentKeyValue(1611658998L);

        Map<String, Object> data = new HashMap<>(8);
        data.put("id", 3);
        data.put("userName", "gx333");
        data.put("orderStatus", 2);
        data.put("userId", "00003");
        dto.setData(data);

        CommonResult result = esWriteService.commonSave(dto);
        log.info(JSON.toJSONString(result));
        return result;
    }

    @GetMapping("/nestedSave")
    public CommonResult nestedSave() {
        EsNestedWriteDto dto = EsNestedWriteDto.builder().build();
        dto.setIndexName("class_student");
        dto.setDocIdKey("id");
        dto.setDocIdValue("2");
        dto.setIdempotentKey("updTime");
        dto.setIdempotentKeyValue(1L);
        dto.setNestedField("students");
        dto.setNestedIdKey("id");
        dto.setNestedIdKeyValue("2");

        Map<String, Object> data = new HashMap<>(8);
        data.put("id", "2");
        data.put("name", "科比2");
        data.put("sex", "男");
        data.put("age", 33);
        data.put("updTime", 1);
        dto.setData(data);

        CommonResult result = esWriteService.nestedSave(dto);
        log.info(JSON.toJSONString(result));
        return result;
    }
}
