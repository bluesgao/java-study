package com.bluesgao.java.study.healthcheck;

/**
 * ClassName: HealthCheckMain
 * Desc: TODO
 * Author: gaoxin11
 * Date: 2018/7/6 10:36
 **/
public class HealthCheckMain {
    public static void main(String[] args) {
        boolean result=false;
        try {
            result = ApplicationStartupUtils.checkServices();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("services validation completed !! Result was :: "+ result);
    }
}
