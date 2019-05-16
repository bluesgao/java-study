package com.bluesgao.threaddemo;

public class Job implements Runnable {
    public void run() {
        System.out.println(Thread.currentThread().getName() + "正在执行...");

    }
}
