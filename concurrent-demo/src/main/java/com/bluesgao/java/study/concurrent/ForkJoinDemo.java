package com.bluesgao.java.study.concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * ClassName: ForkJoinDemo
 * Desc: TODO
 * Author: gaoxin11
 * Date: 2018/6/12 18:31
 **/

public class ForkJoinDemo extends RecursiveTask<Long> {//继承RecursiveTask来实现
    //设立一个最大计算容量
    private final int DEFAULT_CAPACITY = 1000000;


    //用2个数字表示目前要计算的范围
    private int start;

    private int end;

    public ForkJoinDemo(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {//实现compute方法
        //分为两种情况进行出来
        long sum = 0;
        //如果任务量在最大容量之内
        if (end - start < DEFAULT_CAPACITY) {
            for (int i = start; i < end; i++) {
                sum += i;
            }
        } else {//如果超过了最大容量，那么就进行拆分处理
            //计算容量中间值
            int middle = (start + end) / 2;
            //进行递归
            ForkJoinDemo fockJoinTest1 = new ForkJoinDemo(start, middle);
            ForkJoinDemo fockJoinTest2 = new ForkJoinDemo(middle, end);
            //执行任务
            fockJoinTest1.fork();
            fockJoinTest2.fork();
            //等待任务执行并返回结果
            sum = fockJoinTest1.join() + fockJoinTest2.join();
        }
        System.out.println("线程" + Thread.currentThread().getName());
        return sum;
    }


    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinDemo fockJoinTest = new ForkJoinDemo(1, 10000000);
        long fockhoinStartTime = System.currentTimeMillis();
        //前面我们说过，任务提交中invoke可以直接返回结果
        long result = forkJoinPool.invoke(fockJoinTest);
        showLog(forkJoinPool);
        System.out.println("fock/join计算结果耗时" + (System.currentTimeMillis() - fockhoinStartTime) + ",sum=" + result);

        long sum = 0;
        long normalStartTime = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            sum += i;
        }
        System.out.println("普通计算结果耗时" + (System.currentTimeMillis() - normalStartTime) + ",sum=" + sum);
    }

    // 实现 showLog() 方法。它接收 ForkJoinPool 对象作为参数和写关于线程和任务的执行的状态的信息。
    private static void showLog(ForkJoinPool pool) {
        System.out.printf("**********************\n");
        System.out.printf("Main: Fork/Join Pool log\n");
        System.out.printf("Main: Fork/Join Pool: Parallelism:%d\n",
                pool.getParallelism());
        System.out.printf("Main: Fork/Join Pool: Pool Size:%d\n",
                pool.getPoolSize());
        System.out.printf("Main: Fork/Join Pool: Active Thread Count:%d\n",
                pool.getActiveThreadCount());
        System.out.printf("Main: Fork/Join Pool: Running Thread Count:%d\n",
                pool.getRunningThreadCount());
        System.out.printf("Main: Fork/Join Pool: Queued Submission:%d\n",
                pool.getQueuedSubmissionCount());
        System.out.printf("Main: Fork/Join Pool: Queued Tasks:%d\n",
                pool.getQueuedTaskCount());
        System.out.printf("Main: Fork/Join Pool: Queued Submissions:%s\n",
                pool.hasQueuedSubmissions());
        System.out.printf("Main: Fork/Join Pool: Steal Count:%d\n",
                pool.getStealCount());
        System.out.printf("Main: Fork/Join Pool: Terminated :%s\n",
                pool.isTerminated());
        System.out.printf("**********************\n");
    }

}
