package com.bluesgao.storm.trident.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Order implements Serializable {
    private String orderNo;
    private Date payTime;
    private Long amount;
}
