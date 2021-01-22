package com.bluesgao.threaddemo.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExceptionThreadSubmitDemo {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            Future<?> future = exec.submit(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(3 / 0);
            });
            try {
                Object o = future.get(1000, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                System.out.println("捕获异常：" + e.getMessage());
            }
        }
        exec.shutdown();
    }
}
