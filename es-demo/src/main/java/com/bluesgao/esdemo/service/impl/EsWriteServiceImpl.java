package com.bluesgao.esdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.bluesgao.esdemo.common.CommonResult;
import com.bluesgao.esdemo.common.ResultCode;
import com.bluesgao.esdemo.entity.write.EsBaseWriteDto;
import com.bluesgao.esdemo.entity.write.EsCommonWriteDto;
import com.bluesgao.esdemo.entity.write.EsNestedWriteDto;
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
import java.util.Arrays;
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
    public CommonResult<Long> commonSave(EsCommonWriteDto esCommonWriteDto) {
        log.info("search commonSave dto:{}", JSON.toJSONString(esCommonWriteDto));
        String commonErrMsg = commonCheck(esCommonWriteDto);
        if (!StringUtils.isEmpty(commonErrMsg)) {
            log.error("params error:{},esCommonWriteDto:{}", commonErrMsg, JSON.toJSONString(esCommonWriteDto));
            return CommonResult.fail(ResultCode.PARAM_ERROR.getCode(), ResultCode.PARAM_ERROR.getMessage() + commonErrMsg);
        }
        //文档id
        String docId = getDocId(esCommonWriteDto);

        long count = 0;

        //非嵌套模式，保证幂等性的前提下，直接upsert即可

        UpdateRequest request = new UpdateRequest(esCommonWriteDto.getIndexName(), docId);

        //首先获取idempotentKey对应的值，然后比较新老idempotentKey的大小，小于等于不处理，大于更新对应的字段值
        //String code = "String idempotentKey=params.idempotentKey; if(null == ctx._source.get(idempotentKey) || 'null'.equals(ctx._source.get(idempotentKey)) || null == params.data.get(idempotentKey) || Long.valueOf(ctx._source.get(idempotentKey)) < params.data.get(idempotentKey)) {for(entry in params.data.entrySet()) {ctx._source[entry.getKey()] = entry.getValue()}} else{ctx.op='noop';}";
        //ctx.op='noop' 防止不符合条件的更新操作，导致version自增
        String scriptStr = "String idempotentKey = params.idempotentKey; if(null == ctx._source.get(idempotentKey) || null == params.data.get(idempotentKey) || Long.valueOf(ctx._source.get(idempotentKey)) < params.data.get(idempotentKey)){ for(entry in params.data.entrySet()) { ctx._source[entry.getKey()] = entry.getValue(); } }else{ ctx.op='noop'; }";

        Map<String, Object> scriptParams = new HashMap<>(16);
        //幂等key-value
        scriptParams.put("idempotentKey", esCommonWriteDto.getIdempotentKey());
        esCommonWriteDto.getData().put(esCommonWriteDto.getIdempotentKey(), esCommonWriteDto.getIdempotentKeyValue());
        scriptParams.put("data", esCommonWriteDto.getData());

        Script script = new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG, scriptStr, scriptParams);
        request.script(script);//脚本
        //request.id(esCommonWriteDto.getDocIdValue());//docid
        //error: can't provide both script and doc
        //request.docAsUpsert(true);//使用upsert模式
        //request.doc(esCommonWriteDto.getData());
        //request.version((Long) esCommonWriteDto.getData().get("version"));
        //request.versionType(VersionType.EXTERNAL);

        //error: document_missing_exception
        request.upsert(esCommonWriteDto.getData());
        //request.scriptedUpsert(true);
        UpdateResponse response = null;
        try {
            response = client.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("upsert:" + response.getId());
        count = response.getResult() == DocWriteResponse.Result.NOOP ? 0 : 1;

        return CommonResult.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), count);
    }

    @Override
    public CommonResult nestedSave(EsNestedWriteDto esNestedWriteDto) {
        log.info("search nestedSave dto:{}", JSON.toJSONString(esNestedWriteDto));
        String errMsg = nestedCheck(esNestedWriteDto);
        if (!StringUtils.isEmpty(errMsg)) {
            log.error("params error:{},esNestedWriteDto:{}", errMsg, JSON.toJSONString(esNestedWriteDto));
            return CommonResult.fail(ResultCode.PARAM_ERROR.getCode(), ResultCode.PARAM_ERROR.getMessage() + errMsg);

        }
        //文档id
        String docId = getDocId(esNestedWriteDto);

        long count = 0;

        //嵌套模式，嵌套对象需要保证幂等性，同上还要处理主表数据为空的情况
        UpdateRequest request = new UpdateRequest(esNestedWriteDto.getIndexName(), docId);

        // 如果嵌套对象不存在，则把本次对象转成数组存入;若嵌套对象存在，则循环判断本次对象是否已经存在，不存在则插入,存在则判断offset，满足则修改

        String scriptStr = "String nestedField=params.nestedField; if (ctx._source.get(nestedField) == null || ctx._source.get(nestedField).size()==0){ ArrayList arrs = new ArrayList(); arrs.add(params.nestedData); ctx._source.put(nestedField,arrs); }else{ String nestedIdKey=params.nestedIdKey; String nestedIdKeyValue=params.nestedIdKeyValue; boolean updateFlag = false; for(int i=0;i<ctx._source.get(nestedField).size(); i++){ String tempNestedKeyValue = String.valueOf(ctx._source.get(nestedField)[i].get(nestedIdKey)); if(nestedIdKeyValue.equals(tempNestedKeyValue)){ updateFlag = true; String idempotentKey = params.idempotentKey; long idempotentKeyValue = params.idempotentKeyValue; long old = ctx._source.get(nestedField)[i].get(idempotentKey); if(idempotentKeyValue>old){ params.nestedData.forEach((k, v) -> {ctx._source.get(nestedField).get(i)[k] = v}); }else{ ctx.op='noop'; } break; } } if(!updateFlag){ ctx._source.get(nestedField).add(params.nestedData); } }";

        //脚本参数
        Map<String, Object> scriptParams = new HashMap<>(16);
        //幂等key-value
        scriptParams.put("idempotentKey", esNestedWriteDto.getIdempotentKey());
        scriptParams.put("idempotentKeyValue", esNestedWriteDto.getIdempotentKeyValue());
        scriptParams.put("nestedField", esNestedWriteDto.getNestedField());
        scriptParams.put("nestedData", esNestedWriteDto.getData());
        scriptParams.put("nestedIdKey", esNestedWriteDto.getNestedIdKey());
        scriptParams.put("nestedIdKeyValue", esNestedWriteDto.getNestedIdKeyValue());

        //upsert参数
        Map<String, Object> upsertParams = new HashMap<>(8);
        upsertParams.put("id", docId);

        //嵌套字段必须是数组
        upsertParams.put(esNestedWriteDto.getNestedField(), Arrays.asList(esNestedWriteDto.getData()));

        Script script = new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG, scriptStr, scriptParams);
        request.script(script);
        request.upsert(upsertParams);
        UpdateResponse response = null;
        try {
            response = client.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        count = response.getResult() == DocWriteResponse.Result.NOOP ? 0 : 1;

        return CommonResult.success(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), count);
    }

    private String getDocId(Object obj) {
        String docId = null;
        if (obj instanceof EsBaseWriteDto) {
            EsBaseWriteDto esBaseWriteDto = (EsBaseWriteDto) obj;
            if (esBaseWriteDto.getDocIdValue() != null) {
                docId = esBaseWriteDto.getDocIdValue();
            } else {
                docId = esBaseWriteDto.getData().get(esBaseWriteDto.getDocIdKey()).toString();
            }
        }

        return docId;
    }

    private String nestedCheck(EsNestedWriteDto dto) {
        StringBuilder errMsg = new StringBuilder();
        if (dto == null || CollectionUtils.isEmpty(dto.getData())) {
            errMsg.append("EsNestedWriteDto为空;");
        } else if (StringUtils.isEmpty(dto.getDocIdValue())) {
            errMsg.append("DocIdValue为空;");
        } else if (StringUtils.isEmpty(dto.getIndexName())) {
            errMsg.append("IndexName为空;");
        } else if (StringUtils.isEmpty(dto.getIdempotentKey())) {
            errMsg.append("IdempotentKey为空;");
        } else if (dto.getIdempotentKeyValue() == null) {
            errMsg.append("IdempotentKeyValue不能为空且必须为Long;");
        } else if (StringUtils.isEmpty(dto.getDocIdValue())) {
            errMsg.append("DocIdValue为空;");
        } else if (StringUtils.isEmpty(dto.getNestedField())) {
            errMsg.append("NestedField为空;");
        } else if (StringUtils.isEmpty(dto.getNestedIdKey())) {
            errMsg.append("NestedIdKey为空;");
        } else if (StringUtils.isEmpty(dto.getNestedIdKeyValue())) {
            errMsg.append("NestedIdKeyValue为空;");
        }
        return errMsg.toString();
    }


    private String commonCheck(EsCommonWriteDto dto) {
        StringBuilder errMsg = new StringBuilder();
        if (dto == null || CollectionUtils.isEmpty(dto.getData())) {
            errMsg.append("esWriteDto为空;");
        } else if (StringUtils.isEmpty(dto.getDocIdValue())) {
            errMsg.append("DocIdValue为空;");
        } else if (StringUtils.isEmpty(dto.getIndexName())) {
            errMsg.append("IndexName为空;");
        } else if (StringUtils.isEmpty(dto.getIdempotentKey())) {
            errMsg.append("IdempotentKey为空;");
        } else if (dto.getIdempotentKeyValue() == null) {
            errMsg.append("IdempotentKeyValue不能为空且必须为Long;");
        } else if (StringUtils.isEmpty(dto.getDocIdValue())) {
            errMsg.append("DocIdValue为空;");
        }

        return errMsg.toString();
    }
}
