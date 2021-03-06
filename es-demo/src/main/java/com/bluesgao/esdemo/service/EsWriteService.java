package com.bluesgao.esdemo.service;

import com.bluesgao.esdemo.common.CommonResult;
import com.bluesgao.esdemo.entity.write.EsCommonWriteDto;
import com.bluesgao.esdemo.entity.write.EsNestedWriteDto;

/**
 * @ClassName：EsOperateService
 * @Description：
 * @Author：bluesgao
 * @Date：2021/1/26 17:11
 **/
public interface EsWriteService {
    CommonResult commonSave(EsCommonWriteDto esCommonWriteDto);

    CommonResult nestedSave(EsNestedWriteDto esNestedWriteDto);
}
