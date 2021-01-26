package com.bluesgao.esdemo.service;

import com.bluesgao.esdemo.entity.EsWriteDto;

/**
 * @ClassName：EsOperateService
 * @Description：
 * @Author：bluesgao
 * @Date：2021/1/26 17:11
 **/
public interface EsWriteService {
    long save(EsWriteDto esWriteDto);
}
