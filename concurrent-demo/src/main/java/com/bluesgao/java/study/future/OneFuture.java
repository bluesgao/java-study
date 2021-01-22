package com.bluesgao.java.study.future;

import java.util.Random;
import java.util.concurrent.*;

public class OneFuture {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Future<Integer> future = executorService.submit(new Task());
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

    static class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            TimeUnit.MILLISECONDS.sleep(1000);
            return new Random().nextInt();
        }
    }
}
