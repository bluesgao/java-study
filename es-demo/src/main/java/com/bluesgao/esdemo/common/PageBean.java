package com.bluesgao.esdemo.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName：PageBean
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/1 09:42
 **/
@Data
public class PageBean implements Serializable {
    private int pageNo;
    private int pageSize;
    private long totalCount;
    private long totalPages;

    public PageBean() {
        this.pageNo = 1;
        this.pageSize = 10;
    }

    public PageBean(int pageNo, int pageSize) {
        this.pageNo = pageNo < 0 ? 1 : pageNo;
        this.pageSize = pageSize <= 0 ? 10 : pageSize;
    }
}
