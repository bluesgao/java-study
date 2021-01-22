package com.bluesgao.tx.demo.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bluesgao.tx.demo.mapper.OrderMapper;
import java.util.List;
import com.bluesgao.tx.demo.entity.Order;
import com.bluesgao.tx.demo.service.OrderService;
/**
*
*@ClassName：OrderServiceImpl
*@Description：
*@Author：bluesgao
*@Date：2021/1/22 11:16
*
**/
@Service
public class OrderServiceImpl implements OrderService{

    @Resource
    private OrderMapper orderMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Order record) {
        return orderMapper.insert(record);
    }

    @Override
    public int insertOrUpdate(Order record) {
        return orderMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(Order record) {
        return orderMapper.insertOrUpdateSelective(record);
    }

    @Override
    public int insertSelective(Order record) {
        return orderMapper.insertSelective(record);
    }

    @Override
    public Order selectByPrimaryKey(Long id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Order record) {
        return orderMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Order record) {
        return orderMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<Order> list) {
        return orderMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<Order> list) {
        return orderMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<Order> list) {
        return orderMapper.batchInsert(list);
    }

}
