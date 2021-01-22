package com.bluesgao.tx.demo.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.bluesgao.tx.demo.entity.OrderGoods;
import com.bluesgao.tx.demo.mapper.OrderGoodsMapper;
import com.bluesgao.tx.demo.service.OrderGoodsService;
/**
*
*@ClassName：OrderGoodsServiceImpl
*@Description：
*@Author：bluesgao
*@Date：2021/1/22 11:16
*
**/
@Service
public class OrderGoodsServiceImpl implements OrderGoodsService{

    @Resource
    private OrderGoodsMapper orderGoodsMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return orderGoodsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OrderGoods record) {
        return orderGoodsMapper.insert(record);
    }

    @Override
    public int insertOrUpdate(OrderGoods record) {
        return orderGoodsMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(OrderGoods record) {
        return orderGoodsMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int insertSelective(OrderGoods record) {
        return orderGoodsMapper.insertSelective(record);
    }

    @Override
    public OrderGoods selectByPrimaryKey(Long id) {
        return orderGoodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OrderGoods record) {
        return orderGoodsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OrderGoods record) {
        return orderGoodsMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<OrderGoods> list) {
        return orderGoodsMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<OrderGoods> list) {
        return orderGoodsMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<OrderGoods> list) {
        return orderGoodsMapper.batchInsert(list);
    }

}
