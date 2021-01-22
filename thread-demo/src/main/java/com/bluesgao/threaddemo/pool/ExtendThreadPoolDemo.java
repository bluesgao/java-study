package com.bluesgao.threaddemo.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ExtendThreadPoolDemo {
    public static void main(String[] args) {
        System.out.println("start");
        ExtendThreadPoolExecutor executor = new ExtendThreadPoolExecutor(1, 1,
                1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));

        for (int i = 0; i < 10; i++) {
            //submit不会新建线程
            executor.submit(new ExceptionJob(i));

            //execute会新建worker线程
            //executor.execute(new ExceptionJob(i));
        }

        try {
            executor.shutdown();
            System.out.println("ActiveCount:" + executor.getActiveCount());
            System.out.println("TaskCount:" + executor.getTaskCount());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
