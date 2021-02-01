package com.bluesgao.esdemo.entity.es;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
public class SearchResultDto implements Serializable {
    private static final long serialVersionUID = -6483601417109566990L;
    private Map<String, Object> dataMap;
    private String idValue;
}
