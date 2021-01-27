package com.bluesgao.esdemo.entity;

import lombok.Builder;
import lombok.Data;

/**
 * ES通用存储对象
 */
@Data
@Builder
public class EsCommonWriteDto extends EsBaseWriteDto {

    /**
     * 保证幂等性的key
     */
    private String idempotentKey;

    /**
     * 保证幂等性的key的value
     */
    private Long idempotentKeyValue;
}
