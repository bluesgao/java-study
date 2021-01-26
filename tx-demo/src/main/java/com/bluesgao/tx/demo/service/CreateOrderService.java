package com.bluesgao.tx.demo.service;

import com.bluesgao.tx.demo.entity.Order;
import com.bluesgao.tx.demo.entity.OrderGoods;

import java.util.List;

/**
 * @ClassName：CreateOrderService
 * @Description：
 * @Author：bluesgao
 * @Date：2021/1/22 11:19
 **/
public interface CreateOrderService {
    boolean createOrder(Order order, List<OrderGoods> goodsList);
}
