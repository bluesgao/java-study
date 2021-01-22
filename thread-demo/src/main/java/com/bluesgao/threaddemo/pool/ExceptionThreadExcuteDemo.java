package com.bluesgao.threaddemo.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExceptionThreadExcuteDemo {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            exec.execute(() -> {
                System.out.println(3 / 0);
            });
        }
        exec.shutdown();
    }
}
