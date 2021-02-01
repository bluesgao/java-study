package com.bluesgao.esdemo.common;

import java.io.Serializable;

/**
 * @ClassName：PageResult
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/1 09:41
 **/
public class PageResult<T> implements Serializable {
    private String code;
    private String msg;
    private T data;
    private PageBean pageBean;

    private PageResult() {
    }

    public PageResult(String code, String msg, T data, PageBean pageBean) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.pageBean = pageBean;
    }

    public static <T> PageResult fail(String code, String msg) {
        return new PageResult(code, msg, null, null);
    }

    public static <T> PageResult success(T data, PageBean pageBean) {
        return new PageResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data, pageBean);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PageBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }
}
