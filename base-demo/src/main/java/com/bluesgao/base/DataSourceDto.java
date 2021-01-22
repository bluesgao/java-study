package com.bluesgao.base;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
public class DataSourceDto {
    private String id;
    private String type;
    private Map<String, String> ds;
}
