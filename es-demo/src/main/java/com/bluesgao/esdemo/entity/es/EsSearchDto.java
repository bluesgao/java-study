package com.bluesgao.esdemo.entity.es;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class EsSearchDto implements Serializable {
    private static final long serialVersionUID = -5433935615008981178L;
    private String idxName;//索引名称

    private Integer pageNo;//页码

    private Integer pageSize;//每页数量

    private Map<String, String> matchMap;

    private Map<String, List<String>> filterMap;

    private Map<String, List<String>> mustNotMap;

    private Map<String, RangeDto> rangeMap;

    private Map<String, Map<String, List<String>>> nestedMap;//嵌套

    private String[] includeFields;//包含的字段

    private String[] excludeFields;//排除的字段

    private List<SortDto> sortList;//排序字段

    private String[] ids;//按照id查询

    public EsSearchDto(String idxName, Integer pageNo, Integer pageSize) {
        this.idxName = idxName;
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
