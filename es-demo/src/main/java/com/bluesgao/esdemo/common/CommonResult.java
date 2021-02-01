package com.bluesgao.esdemo.common;


import java.io.Serializable;

/**
 * 数据获取处理结果
 */
public class CommonResult<T> implements Serializable {
    private String code;
    private String msg;
    private T data;

    private CommonResult() {
    }

    public CommonResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> CommonResult success(String code, String msg, T data) {
        return new CommonResult(code, msg, data);
    }

    public static CommonResult fail(String code, String msg) {
        return new CommonResult(code, msg, null);
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
