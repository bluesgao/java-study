package com.bluesgao.redis;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class RedisTest {
    /**
     * thread    batch   expend_time(s)
     * 100       100     1.371
     * 100       1000    6.375
     * 100       10000   113.856
     * @param args
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        //个工作者线程：CPU核数*2
        int taskCount = 2 * Runtime.getRuntime().availableProcessors();

        ExecutorService executor = Executors.newCachedThreadPool();

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
