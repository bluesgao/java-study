package com.bluesgao.java.study.future;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class MakeTea {
    public static void main(String[] args) throws Exception {
        FutureTask<String> futureTask2 = new FutureTask<>(new T2());
        FutureTask<String> futureTask1 = new FutureTask<>(new T1(futureTask2));

        new Thread(futureTask1).start();
        new Thread(futureTask2).start();

        System.out.println(futureTask1.get());
    }
}


// T2Task需要执行的任务:洗茶壶、洗茶杯、拿茶叶
class T2 implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("T2:洗茶壶...");
        TimeUnit.SECONDS.sleep(1);

        System.out.println("T2:洗茶杯...");
        TimeUnit.SECONDS.sleep(2);

        System.out.println("T2:拿茶叶...");
        TimeUnit.SECONDS.sleep(1);

        return "龙井";
    }
}


// T1Task需要执行的任务：洗水壶、烧开水、泡茶
class T1 implements Callable<String> {

    private FutureTask<String> task;

    T1(FutureTask<String> task) {
        this.task = task;
    }

    @Override
    public String call() throws Exception {
        System.out.println("T1:洗水壶...");
        TimeUnit.SECONDS.sleep(1);

        System.out.println("T1:烧开水...");
        TimeUnit.SECONDS.sleep(15);

        System.out.println("T1:拿到茶叶:" + task.get());

        System.out.println("T1:泡茶...");

        return "上茶:" + task;
    }
}
