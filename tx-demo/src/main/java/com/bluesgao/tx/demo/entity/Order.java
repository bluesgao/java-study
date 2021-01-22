package com.bluesgao.tx.demo.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
*@ClassName：Order
*@Description：
*@Author：bluesgao
*@Date：2021/1/22 11:16
*
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;

    private Long orderSn;

    private Long orderPrice;

    private Integer orderStatus;

    private Date orderTime;

    private Date cTime;

    private Date mTime;

    private Long userId;

    private String userName;
}