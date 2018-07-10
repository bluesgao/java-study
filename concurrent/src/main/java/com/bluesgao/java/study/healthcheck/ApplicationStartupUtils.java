package com.bluesgao.java.study.healthcheck;

import com.bluesgao.java.study.concurrent.NetworkHealthCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * ClassName: ApplicationStartupUtils
 * Desc: TODO
 * Author: gaoxin11
 * Date: 2018/7/6 10:23
 **/
public class ApplicationStartupUtils {
    private static List<BaseHealthCheck> services;
    private static CountDownLatch countDownLatch;

    private ApplicationStartupUtils() {
    }

    private final static ApplicationStartupUtils INSTANCE = new ApplicationStartupUtils();

    public static synchronized ApplicationStartupUtils getInstance() {
        return INSTANCE;
    }

    public static boolean checkServices() throws Exception {
        countDownLatch = new CountDownLatch(3);
        services = new ArrayList<BaseHealthCheck>();
        services.add(new NetworkHealthCheck(countDownLatch));
        services.add(new DataBaseHealthCheck(countDownLatch));
        services.add(new AppHealthCheck(countDownLatch));

        Executor executor = Executors.newFixedThreadPool(services.size());
        for (BaseHealthCheck item : services) {
            executor.execute(item);
        }

        countDownLatch.await();

        for (BaseHealthCheck item : services) {
            if (!item.isServiceUp()) {
                return false;
            }
        }

        return true;
    }
}
