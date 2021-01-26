package com.bluesgao.tx.demo.service.impl;

import com.bluesgao.tx.demo.entity.Order;
import com.bluesgao.tx.demo.entity.OrderGoods;
import com.bluesgao.tx.demo.service.CreateOrderService;
import com.bluesgao.tx.demo.service.OrderGoodsService;
import com.bluesgao.tx.demo.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName：CreateOrderServiceImpl
 * @Description：
 * @Author：bluesgao
 * @Date：2021/1/22 11:33
 **/
@Slf4j
@Service
public class CreateOrderServiceImpl implements CreateOrderService {

    @Resource
    private OrderService orderService;

    @Resource
    private OrderGoodsService orderGoodsService;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean createOrder(Order order, List<OrderGoods> goodsList) {
        int orderRet = orderService.insertSelective(order);
        if (orderRet == 1 && order.getId() >= 0) {
            int goodsRet = orderGoodsService.batchInsert(goodsList);
            if (goodsRet > 0) {
                return true;
            } else {
                new RuntimeException("订单商品插入失败");
            }
        }

        return false;
    }



}
