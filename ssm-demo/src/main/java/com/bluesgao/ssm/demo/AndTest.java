package com.bluesgao.ssm.demo;

import org.apache.commons.lang3.StringUtils;

public class AndTest {
    public static void main(String[] args) {
        String flag = "CD";
        if (!StringUtils.equals(flag, "CF") && !StringUtils.equals(flag, "CD")) {
            System.out.println("111111111111111");
        }
    }
}
