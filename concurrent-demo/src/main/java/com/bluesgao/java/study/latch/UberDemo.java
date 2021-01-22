package com.bluesgao.java.study.latch;

import lombok.extern.java.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 一个线程等待其他多个线程都执行完毕，再继续自己的工作
 * <p>
 * 场景：拼车
 */
@Log
public class UberDemo {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(4);
        ExecutorService es = Executors.newFixedThreadPool(4);

        //System.out.println("司机等待所有乘客上车...");
        log.info("司机等待所有乘客上车...");

        for (int i = 0; i < 4; i++) {
            es.submit(new Passenger(i, latch));
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //System.out.println("所有乘客已上车，司机发车...");
        log.info("所有乘客已上车，司机发车...");
        es.shutdown();
    }

    static class Passenger implements Runnable {
        private Integer id;
        private CountDownLatch latch;

        public Passenger(Integer id, CountDownLatch latch) {
            this.id = id;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(1000 * id);
                //System.out.println(id + "号乘客已上车");
                log.info(id + "号乘客已上车");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }
}
