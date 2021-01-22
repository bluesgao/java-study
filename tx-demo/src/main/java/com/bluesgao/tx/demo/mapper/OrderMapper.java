package com.bluesgao.tx.demo.mapper;

import com.bluesgao.tx.demo.entity.Order;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName：OrderMapper
 * @Description：
 * @Author：bluesgao
 * @Date：2021/1/22 11:26
 **/
public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertOrUpdate(Order record);

    int insertOrUpdateSelective(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int updateBatch(List<Order> list);

    int updateBatchSelective(List<Order> list);

    int batchInsert(@Param("list") List<Order> list);
}