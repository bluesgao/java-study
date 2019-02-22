package com.bluesgao.swaggerdemo.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<V> implements Serializable {
    private int code;
    private String info;
    private V value;
}
