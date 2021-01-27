package com.bluesgao.esdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.bluesgao.esdemo.common.CommonResult;
import com.bluesgao.esdemo.entity.EsWriteDto;
import com.bluesgao.esdemo.service.EsWriteService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName：EsWriteServiceImpl
 * @Description：
 * @Author：bluesgao
 * @Date：2021/1/26 17:12
 **/
@Slf4j
@Service
public class EsWriteServiceImpl implements EsWriteService {
    @Resource
    private RestHighLevelClient client;

    @Override
    public CommonResult save(EsWriteDto esWriteDto) {
        log.info("es save dto:{}", JSON.toJSONString(esWriteDto));
        String errMsg = commonCheck(esWriteDto);
        if (!StringUtils.isEmpty(errMsg)) {
            log.error("params error:{},esWriteDto:{}", errMsg, JSON.toJSONString(esWriteDto));
            return CommonResult.fail("参数错误:" + errMsg);
        }
        long count = 0;

        if (!esWriteDto.isNested()) {
            log.info("非嵌套模式");
            //非嵌套模式，保证幂等性的前提下，直接upsert即可

            //文档id
            String docId = null;
            if (esWriteDto.getDocIdValue() != null) {
                docId = esWriteDto.getDocIdValue();
            } else {
                docId = esWriteDto.getData().get(esWriteDto.getDocIdKey()).toString();
            }
            UpdateRequest request = new UpdateRequest(esWriteDto.getIndexName(), docId);

            //首先获取idempotentKey对应的值，然后比较新老idempotentKey的大小，小于等于不处理，大于更新对应的字段值
            //String code = "String idempotentKey=params.idempotentKey; if(null == ctx._source.get(idempotentKey) || 'null'.equals(ctx._source.get(idempotentKey)) || null == params.data.get(idempotentKey) || Long.valueOf(ctx._source.get(idempotentKey)) < params.data.get(idempotentKey)) {for(entry in params.data.entrySet()) {ctx._source[entry.getKey()] = entry.getValue()}} else{ctx.op='noop';}";
            //ctx.op='noop' 防止不符合条件的更新操作，导致version自增
            String code = "String idempotentKey = params.idempotentKey; if(null == ctx._source.get(idempotentKey) || null == params.data.get(idempotentKey) || Long.valueOf(ctx._source.get(idempotentKey)) < params.data.get(idempotentKey)){ for(entry in params.data.entrySet()) { ctx._source[entry.getKey()] = entry.getValue(); } }else{ ctx.op='noop'; }";

            Map<String, Object> params = new HashMap<>(16);
            //幂等key-value
            params.put("idempotentKey", esWriteDto.getIdempotentKey());
            if (esWriteDto.getIdempotentKeyValue() != null && esWriteDto.getIdempotentKeyValue() >= 0) {
                esWriteDto.getData().put(esWriteDto.getIdempotentKey(), esWriteDto.getIdempotentKeyValue());
            }
            params.put("data", esWriteDto.getData());

            Script script = new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG, code, params);
            request.script(script);//脚本
            //request.id(esWriteDto.getDocIdValue());//docid
            //error: can't provide both script and doc
            //request.docAsUpsert(true);//使用upsert模式
            //request.doc(esWriteDto.getData());
            //request.version((Long) esWriteDto.getData().get("version"));
            //request.versionType(VersionType.EXTERNAL);

            //error: document_missing_exception
            request.upsert(esWriteDto.getData());
            //request.scriptedUpsert(true);
            UpdateResponse response = null;
            try {
                response = client.update(request, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println("upsert:" + response.getId());
            count = response.getResult() == DocWriteResponse.Result.NOOP ? 0 : 1;
        } else {
            log.info("嵌套模式");
            //todo 参数检查

        }
        return CommonResult.success(count);
    }

    private String nestedCheck(EsWriteDto esWriteDto) {
        StringBuilder errMsg = new StringBuilder();
        if (StringUtils.isEmpty(esWriteDto.getNestedField())) {
            errMsg.append("NestedField为空;");
        }

        return errMsg.toString();
    }


    private String commonCheck(EsWriteDto esWriteDto) {
        StringBuilder errMsg = new StringBuilder();
        if (esWriteDto == null || CollectionUtils.isEmpty(esWriteDto.getData())) {
            errMsg.append("esWriteDto为空;");
        } else if (StringUtils.isEmpty(esWriteDto.getIdempotentKey())) {
            errMsg.append("IdempotentKey为空;");
        } else if (StringUtils.isEmpty(esWriteDto.getDocIdKey())) {
            errMsg.append("DocIdKey为空;");
        } else if (StringUtils.isEmpty(esWriteDto.getIndexName())) {
            errMsg.append("IndexName为空;");
        } else if (esWriteDto.getData().get(esWriteDto.getIdempotentKey()) == null) {
            errMsg.append("IdempotentKey对应的value为空;");
        } else if (StringUtils.isEmpty(esWriteDto.getDocIdValue())) {
            errMsg.append("DocIdValue为空;");
        }

        Object idempotentKeyValue = esWriteDto.getData().get(esWriteDto.getIdempotentKey());
        if (!(idempotentKeyValue instanceof Long)) {
            errMsg.append(";IdempotentKey对应的value必须为Long");
        }

        return errMsg.toString();
    }
}
