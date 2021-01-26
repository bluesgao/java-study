package com.bluesgao.tx.demo.service.impl;


import com.bluesgao.tx.demo.config.SpringConfig;
import com.bluesgao.tx.demo.entity.Order;
import com.bluesgao.tx.demo.entity.OrderGoods;
import com.bluesgao.tx.demo.service.CreateOrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName：CreateOrderServiceImplTest
 * @Description：
 * @Author：bluesgao
 * @Date：2021/1/25 09:38
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class CreateOrderServiceImplTest {

    @Autowired
    private CreateOrderService createOrderService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void createOrder() {
        Order order = Order.builder().orderNo("O-002").orderPrice(1L).orderTime(new Date())
                .userId("U-001").userName("test").build();

        OrderGoods goods = OrderGoods.builder().orderNo(order.getOrderNo())
                .goodsNo("G-002").goodsName("mac001").goodsDesc("mac 666")
                .goodsPrice(10000L).goodsNumber(1).goodsImg("img")
                .merchantId("M-001").merchantName("MAC shop")
                .build();

        List<OrderGoods> goodsList = new ArrayList<>();
        goodsList.add(goods);
        createOrderService.createOrder(order, goodsList);
    }
}