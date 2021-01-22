package com.bluesgao.threaddemo.pool;

import java.util.concurrent.TimeUnit;

public class ExceptionJob implements Runnable {
    private int id;

    public ExceptionJob(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        //给线程对象设置异常处理器
        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(Thread.currentThread().getName() + "执行任务失败，执行异常处理器,错误原因:[" + e.getMessage() + "]");
            }
        });
        System.out.println(Thread.currentThread().getName() + "正在执行任务[" + id + "]");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new RuntimeException(Thread.currentThread().getName() + "正在执行任务[" + id + "]异常");
    }
}
