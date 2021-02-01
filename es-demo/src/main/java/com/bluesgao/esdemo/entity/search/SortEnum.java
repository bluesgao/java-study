package com.bluesgao.esdemo.entity.search;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * User: sunliang10
 * Date: 2018/10/24
 * Time: 11:31
 */
@Getter
public enum SortEnum {
    DESC("desc", "降序"),
    ASC("asc", "升序");
    private String code;
    private String msg;

    SortEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
