package com.bluesgao.java.study.concurrent;

import com.bluesgao.java.study.healthcheck.BaseHealthCheck;

import java.util.concurrent.CountDownLatch;

/**
 * ClassName: NetworkHealthCheck
 * Desc: TODO
 * Author: gaoxin11
 * Date: 2018/7/6 10:15
 **/
public class NetworkHealthCheck extends BaseHealthCheck {
    public NetworkHealthCheck(CountDownLatch countDownLatch) {
        super(countDownLatch, "network service");
    }

    @Override
    public void verifyService() {
        System.out.println("checking "+this.getServiceName());

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName()+" is up.");
    }
}
