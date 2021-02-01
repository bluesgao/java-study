package com.bluesgao.esdemo.entity.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NestedDto implements Serializable {
    private static final long serialVersionUID = 3649202759701145894L;

    private Map<String, String> matchMap;

    private Map<String, List<String>> filterMap;

    private Map<String, List<String>> mustNotMap;

    private Map<String, RangeDto> rangeMap;

}
