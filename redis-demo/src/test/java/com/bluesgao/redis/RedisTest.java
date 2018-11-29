package com.bluesgao.redis;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class RedisTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newCachedThreadPool();

        int taskCount = 10;
        CountDownLatch latch = new CountDownLatch(taskCount);
        for (int i = 0; i < taskCount; i++) {
            executor.submit(new DemoTask(latch, true));
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdownNow();

        long end = System.currentTimeMillis();
        log.info("execution finish time(s):{}", (end - start) / 1000.0);
    }
}
