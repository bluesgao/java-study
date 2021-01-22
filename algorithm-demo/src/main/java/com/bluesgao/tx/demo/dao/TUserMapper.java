package com.bluesgao.tx.demo.dao;

import com.bluesgao.tx.demo.entity.TUser;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
*
*@ClassName：TUserMapper
*@Description：
*@Author：bluesgao
*@Date：2021/1/22 10:55
*
**/
public interface TUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TUser record);

    int insertOrUpdate(TUser record);

    int insertOrUpdateSelective(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);

    int updateBatch(List<TUser> list);

    int updateBatchSelective(List<TUser> list);

    int batchInsert(@Param("list") List<TUser> list);
}