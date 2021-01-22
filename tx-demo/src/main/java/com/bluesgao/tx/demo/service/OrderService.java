package com.bluesgao.tx.demo.service;

import java.util.List;
import com.bluesgao.tx.demo.entity.Order;
    /**
*
*@ClassName：OrderService
*@Description：
*@Author：bluesgao
*@Date：2021/1/22 11:16
*
**/
public interface OrderService{


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

    int batchInsert(List<Order> list);

}
