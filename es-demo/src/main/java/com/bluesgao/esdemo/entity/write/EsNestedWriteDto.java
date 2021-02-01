package com.bluesgao.esdemo.entity.write;

import lombok.Builder;
import lombok.Data;

/**
 * ES嵌套存储对象
 */
@Data
@Builder
public class EsNestedWriteDto extends EsBaseWriteDto {

    /**
     * 嵌套字段的key
     */
    private String nestedField;

    /**
     * 关联的业务键,用于子表的crud(父表与子表关联的字段在es中的key)
     */
    //private String relatedKey;
    /**
     * 关联的业务值
     */
    //private String relatedKeyValue;

    /**
     * 保证幂等性的key
     */
    private String idempotentKey;

    /**
     * 保证幂等性的key的value
     */
    private Long idempotentKeyValue;

    /**
     * 嵌套对象的id
     */
    private String nestedIdKey;

    /**
     * 嵌套对象的id的value
     */
    private String nestedIdKeyValue;
}
