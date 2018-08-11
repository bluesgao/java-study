package com.bluesgao.java.study.string;

import java.util.ArrayList;

/**
 * ClassName: StringTest
 * Desc: TODO
 * Author: gaoxin11
 * Date: 2018/5/30 10:51
 **/
public class StringTest {
    public static void main(String[] args) {
        Integer i = 1;

        //产生大量中间对象
        String string = "aaaa"+"bbbb"+"cccc";

        //线程非安全
        String strBuilder = new StringBuilder().append("1111").append("2222").append("3333").append("4444").append("5555").toString();


        //线程安全（通过方法添加synchronized实现）
        String strBuffer = new StringBuffer().append("dddd").append("ffff").append("eeee").append("gggg").append("hhhh").toString();
    }
}
