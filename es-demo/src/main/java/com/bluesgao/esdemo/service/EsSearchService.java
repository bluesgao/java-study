package com.bluesgao.esdemo.service;

import com.bluesgao.esdemo.common.PageResult;
import com.bluesgao.esdemo.entity.es.EsSearchDto;
import com.bluesgao.esdemo.entity.es.SearchResultDto;

import java.util.List;

/**
 * @ClassName：EsSearchService
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/1 09:35
 **/
public interface EsSearchService {
    PageResult<List<SearchResultDto>> commonSearch(EsSearchDto param);

}
