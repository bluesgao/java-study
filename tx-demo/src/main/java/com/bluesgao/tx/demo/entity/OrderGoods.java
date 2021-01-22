package com.bluesgao.tx.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
*@ClassName：OrderGoods
*@Description：
*@Author：bluesgao
*@Date：2021/1/22 11:16
*
**/
/**
    * 订单商品表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderGoods {
    private Long id;

    /**
    * 订单号
    */
    private String orderNo;

    /**
    * 商品编号
    */
    private String goodsNo;

    /**
    * 商家id
    */
    private String merchantId;

    /**
    * 商家名称
    */
    private String merchantName;

    /**
    * 商品数量
    */
    private Integer goodsNumber;

    /**
    * 商品单价
    */
    private Long goodsPrice;

    /**
    * 商品主图
    */
    private String goodsImg;

    /**
    * 商品描述
    */
    private String goodsDesc;

    /**
    * 商品名称
    */
    private String goodsName;
}