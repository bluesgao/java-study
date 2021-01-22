package com.bluesgao.threaddemo.schedule;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TimerDemo {
    public static void main(String[] args) {

        Timer timer = new Timer("定时任务");

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " timer task run " + System.currentTimeMillis());
            }
        }, 0, 1000 * 3);


        try {
            TimeUnit.SECONDS.sleep(10);
            timer.cancel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
