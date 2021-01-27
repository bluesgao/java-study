package com.bluesgao.esdemo.common;


import java.io.Serializable;

/**
 * 数据获取处理结果
 */
public class CommonResult implements Serializable {
    private Boolean success;
    private String msg;
    private Object data;

    private CommonResult() {
    }

    public CommonResult(Boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public static CommonResult success(Object data) {
        return new CommonResult(true, "", data);
    }

    public static CommonResult fail(String msg) {
        return new CommonResult(false, msg, null);
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "DataFetcherResult{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
