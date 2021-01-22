package com.bluesgao.threaddemo.pool;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {
    public static void main(String[] args) {
        int workNum = 4;
        final CountDownLatch latch = new CountDownLatch(workNum);//两个工人的协作
        ExecutorService threadPool = Executors.newFixedThreadPool(workNum);
        for (int i = 0; i < workNum; i++) {
            threadPool.submit(new Runnable() {
                public void run() {
                    try {
                        doWork();//工作了
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }
                }
            });
        }
        try {
            latch.await();//等待所有工人完成工作
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
        System.out.println("all work done at " + new Date());

    }

    private static void doWork() {
        System.out.println("... work work work ...");
    }


}
