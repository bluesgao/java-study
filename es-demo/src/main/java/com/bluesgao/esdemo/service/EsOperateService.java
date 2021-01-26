package com.bluesgao.esdemo.service;

import com.bluesgao.esdemo.entity.EsSaveDto;

/**
 * @ClassName：EsOperateService
 * @Description：
 * @Author：bluesgao
 * @Date：2021/1/26 17:11
 **/
public interface EsOperateService {
    long save(EsSaveDto esSaveDto);
}
