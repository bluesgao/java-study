package com.bluesgao.java.study.healthcheck;

import java.util.concurrent.CountDownLatch;

/**
 * ClassName: AppHealthCheck
 * Desc: TODO
 * Author: gaoxin11
 * Date: 2018/7/6 10:21
 **/
public class AppHealthCheck extends BaseHealthCheck {
    public AppHealthCheck(CountDownLatch countDownLatch) {
        super(countDownLatch, "App Service");
    }

    @Override
    public void verifyService() {
        System.out.println("checking "+this.getServiceName());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName()+" is up.");

    }
}
