package com.bluesgao.esdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.bluesgao.esdemo.entity.EsSaveDto;
import com.bluesgao.esdemo.service.EsOperateService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @ClassName：EsOperateServiceImpl
 * @Description：
 * @Author：bluesgao
 * @Date：2021/1/26 17:12
 **/
@Slf4j
@Service
public class EsOperateServiceImpl implements EsOperateService {
    @Resource
    private RestHighLevelClient client;

    @Override
    public long save(EsSaveDto esSaveDto) {
        log.info("es save dto:{}", JSON.toJSONString(esSaveDto));

        if (!esSaveDto.getIsNested()) {
            log.info("非嵌套模式");
            //非嵌套模式，保证幂等性的前提下，直接upsert即可
            UpdateRequest request = new UpdateRequest(esSaveDto.getIndexName(), esSaveDto.getDocIdValue());

            //首先获取idempotentKey对应的值，然后比较新老idempotentKey的大小，小于等于不处理，大于更新对应的字段值
            String code = "String idempotentKey=params.idempotentKey; if(null == ctx._source.get(idempotentKey) || 'null'.equals(ctx._source.get(idempotentKey)) || null == params.data.get(idempotentKey) || Long.valueOf(ctx._source.get(idempotentKey)) < params.data.get(idempotentKey)) {for(entry in params.data.entrySet()) {ctx._source[entry.getKey()] = entry.getValue()}} else{ctx.op='noop';}";

            Map<String, Object> params = esSaveDto.getData();
            params.putIfAbsent(esSaveDto.getIdempotentKey(), esSaveDto.getIdempotentKeyValue());
            Script script = new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG, code, params);
            request.script(script);//脚本
            request.id(esSaveDto.getDocIdValue());//docid
            request.docAsUpsert(true);//使用upsert模式
            request.doc(esSaveDto.getData());
            //request.version((Long) esSaveDto.getData().get("version"));
            //request.versionType(VersionType.EXTERNAL);


            UpdateResponse response = null;
            try {
                response = client.update(request, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("upsert:" + response.getId());
        } else {
            log.info("嵌套模式");
            //todo
        }
        return 0;
    }
}
