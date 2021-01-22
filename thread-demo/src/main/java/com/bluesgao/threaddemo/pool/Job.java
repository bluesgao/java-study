package com.bluesgao.threaddemo.pool;

public class Job implements Runnable {
    public void run() {
        System.out.println(Thread.currentThread().getName() + "正在执行...");

    }
}
