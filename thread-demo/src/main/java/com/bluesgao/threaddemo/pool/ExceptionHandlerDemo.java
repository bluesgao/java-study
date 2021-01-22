package com.bluesgao.threaddemo.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 只有通过execute提交的任务，才能将它抛出的异常交给UncaughtExceptionHandler，
 * 而通过submit提交的任务，无论是抛出的未检测异常还是已检查异常，都将被认为是任务返回状态的一部分。
 * 如果一个由submit提交的任务由于抛出了异常而结束，那么这个异常将被Future.get封装在ExecutionException中重新抛出
 */
public class ExceptionHandlerDemo {
    public static void main(String[] args) throws Exception {
        //executorSubmit();
        executorExecute();
    }

    private static void executorSubmit() {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            Future<?> future = exec.submit(new ExceptionJob(i));
            Object obj = null;
            try {
                obj = future.get(1000, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                System.out.println("future error:" + e);
                e.printStackTrace();
            }
            System.out.println("future obj:" + obj);
        }
        exec.shutdown();
    }

    private static void executorExecute() throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            exec.execute(new ExceptionJob(i));
        }
        exec.shutdown();
    }
}
