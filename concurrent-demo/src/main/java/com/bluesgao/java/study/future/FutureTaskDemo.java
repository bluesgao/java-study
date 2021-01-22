package com.bluesgao.java.study.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskDemo {
    public static void main(String[] args) {
        Task t = new Task();
        FutureTask<Integer> ft = new FutureTask<>(t);

        new Thread(ft).start();

        try {
            System.out.println("task运行结果："+ ft.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
    static class Task implements Callable<Integer>{
        @Override
        public Integer call() throws Exception {
            System.out.println("子线程正在计算");
            int sum = 0;
            for (int i=0;i<1000;i++){
                sum = sum+i;
            }
            return sum;
        }
    }
}



