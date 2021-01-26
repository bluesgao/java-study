package com.bluesgao.esdemo.entity;

import lombok.Data;

import java.util.Map;

/**
 * ES存储对象
 */
@Data
public class EsSaveDto {
    /**
     * 需要存入的数据
     */
    private Map<String, Object> data;

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
     * 是否是嵌套模式
     */
    private Boolean isNested;

    /**
     * 嵌套字段的key
     */
    private String nestedField;

    /**
     * 关联的业务键,用于子表的crud(父表与子表关联的字段在es中的key)
     */
    private String relatedKey;
    /**
     * 关联的业务值
     */
    private String relatedKeyValue;

    /**
     * 非嵌套非主表必传，用于存储平铺子表的offset-key
     */
    private String offsetKey;

    /**
     * 保证幂等性的key
     */
    private String idempotentKey;

    /**
     * 保证幂等性的key的value
     */
    private Long idempotentKeyValue;

    /**
     * 重试次数
     */
    private Integer retryCount;


    private long hashCode;

    private Boolean refresh;
}
