package com.bluesgao.utils;

/**
 * @ClassName：ParamErrEnum
 * @Description：
 * @Author：bluesgao
 * @Date：2021/3/4 11:50
 **/
public enum ParamErrEnum {
    NOT_NULL("不能为空"),
    NOT_SMALLER_ZEOR("不能为空，且不能小于0");

    String msg;

    ParamErrEnum(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
