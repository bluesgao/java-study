package com.bluesgao.java.study.healthcheck;

import java.util.concurrent.CountDownLatch;

/**
 * ClassName: BaseHealthCheck
 * Desc: TODO
 * Author: gaoxin11
 * Date: 2018/7/6 9:50
 **/
public abstract class BaseHealthCheck implements Runnable{
    private CountDownLatch countDownLatch;
    private String serviceName;
    private boolean serviceUp;

    public BaseHealthCheck(CountDownLatch countDownLatch, String serviceName) {
        this.countDownLatch = countDownLatch;
        this.serviceName = serviceName;
        this.serviceUp = false;
    }

    public void run() {
        try {
            verifyService();
            serviceUp = true;
        } catch (Exception e) {
            e.printStackTrace();
            serviceUp = false;
        }finally {
            if(countDownLatch != null){
                countDownLatch.countDown();
            }
        }
    }

    public String getServiceName(){
        return serviceName;
    }

    public boolean isServiceUp() {
        return serviceUp;
    }

    public abstract void verifyService();
}
