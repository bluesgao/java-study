package com.bluesgao.java.study.healthcheck;

import java.util.concurrent.CountDownLatch;

/**
 * ClassName: DataBaseHealthCheck
 * Desc: TODO
 * Author: gaoxin11
 * Date: 2018/7/6 10:19
 **/
public class DataBaseHealthCheck extends BaseHealthCheck {
    public DataBaseHealthCheck(CountDownLatch countDownLatch) {
        super(countDownLatch, "DataBase Service");
    }

    @Override
    public void verifyService() {
        System.out.println("checking "+this.getServiceName());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName()+" is up.");

    }
}
