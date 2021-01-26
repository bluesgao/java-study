package com.bluesgao.esdemo.controller.admin;

import com.bluesgao.esdemo.entity.EsSaveDto;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

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
    private RestHighLevelClient client;

    @PostMapping("/save/")
    public boolean save(@RequestBody EsSaveDto esSaveDto) {
        UpdateRequest request = new UpdateRequest(esSaveDto.getIndexName(), esSaveDto.getDocIdValue());

        Script inline = new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG,
                "ctx._source.field += params.count", esSaveDto.getData());
        request.script(inline);
        request.docAsUpsert(true);
        request.doc(esSaveDto.getData());
        request.version((Long) esSaveDto.getData().get("version"));
        request.versionType(VersionType.EXTERNAL);


        String code = "String offsetKey=params.offsetKey;if(null == ctx._source.get(offsetKey) || 'null'.equals(ctx._source.get(offsetKey)) || null == params.data.get(offsetKey) || Long.valueOf(ctx._source.get(offsetKey)) < params.data.get(offsetKey))"
                + "{for(entry in params.data.entrySet()) {ctx._source[entry.getKey()] = entry.getValue()}} "
                + "else{ctx.op='noop';}";

        UpdateResponse response = null;
        try {
            response = client.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("upsert:" + response.getId());
        return true;
    }
}
