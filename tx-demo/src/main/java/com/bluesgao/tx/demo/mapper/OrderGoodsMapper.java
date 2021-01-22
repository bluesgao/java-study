package com.bluesgao.tx.demo.mapper;

import com.bluesgao.tx.demo.entity.OrderGoods;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
*
*@ClassName：OrderGoodsMapper
*@Description：
*@Author：bluesgao
*@Date：2021/1/22 11:16
*
**/
public interface OrderGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderGoods record);

    int insertOrUpdate(OrderGoods record);

    int insertOrUpdateSelective(OrderGoods record);

    int insertSelective(OrderGoods record);

    OrderGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderGoods record);

    int updateByPrimaryKey(OrderGoods record);

    int updateBatch(List<OrderGoods> list);

    int updateBatchSelective(List<OrderGoods> list);

    int batchInsert(@Param("list") List<OrderGoods> list);
}