package com.bluesgao.java.study.latch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 多个线程等待某一个线程的信号，同时开始执行
 * 场景：马拉松比赛
 */
public class MarathonDemo {

    public static void main(String[] args) throws Exception {
        //100个参赛选手
        ExecutorService es = Executors.newFixedThreadPool(100);
        //比赛开始指令
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < 100; i++) {
            es.submit(new Runner(i, latch));
        }

        System.out.println("距离比赛还有10秒,倒计时开始...");
        for (int i = 10; i > 0; i--) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("倒计时开始..." + i);
        }
        System.out.println("发令枪响，比赛开始...");
        latch.countDown();

        es.shutdown();
        if (es.awaitTermination(2 * 60, TimeUnit.SECONDS)) {
            System.out.println("比赛规定时间已经到,比赛结束...");
        }
    }

    static class Runner implements Runnable {
        private Integer id;
        private CountDownLatch latch;

        public Runner(Integer id, CountDownLatch latch) {
            this.id = id;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                System.out.println(id + "号运动员准备完毕，等待指令");
                latch.await();
                System.out.println(id + "号运动员起跑...");
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(20) * 50);
                System.out.println(id + "号运动员到达终点...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
