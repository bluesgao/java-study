package com.bluesgao.esdemo.entity.search;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class EsSearchDto implements Serializable {
    private static final long serialVersionUID = -5433935615008981178L;
    private String indexName;//索引名称

    private int pageNo = 1;//页码

    private int pageSize = 10;//每页数量

    private Map<String, String> matchMap;

    private Map<String, List<String>> filterMap;

    private Map<String, List<String>> mustNotMap;

    private Map<String, RangeDto> rangeMap;

    private Map<String, NestedDto> nestedMap;//嵌套

    private String[] includeFields;//包含的字段

    private String[] excludeFields;//排除的字段

    private List<SortDto> sortList;//排序字段

    private String[] ids;//按照id查询

    public EsSearchDto(String indexName, Integer pageNo, Integer pageSize) {
        this.indexName = indexName;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public EsSearchDto() {
    }

    public void clearMapParam() {
        this.matchMap = null;
        this.filterMap = null;
        this.mustNotMap = null;
        this.rangeMap = null;
        this.sortList = null;
    }
}
