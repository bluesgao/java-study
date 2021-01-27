package com.bluesgao.esdemo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName：EsBaseWriteDto
 * @Description：
 * @Author：bluesgao
 * @Date：2021/1/27 16:22
 **/
@Data
public class EsBaseWriteDto implements Serializable {
    /**
     * doc id 字段
     */
    private String docIdKey;
    /**
     * doc id value
     */
    private String docIdValue;
    /**
     * 索引名
     */
    private String indexName;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 需要存入的数据
     */
    private Map<String, Object> data;
}
