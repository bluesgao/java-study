package com.bluesgao.tx.demo.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName：Order
 * @Description：
 * @Author：bluesgao
 * @Date：2021/1/22 11:26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单金额
     */
    private Long orderPrice;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 用户号
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 下单时间
     */
    private Date orderTime;

    /**
     * 创建时间
     */
    private Date cTime;

    /**
     * 修改时间
     */
    private Date mTime;
}