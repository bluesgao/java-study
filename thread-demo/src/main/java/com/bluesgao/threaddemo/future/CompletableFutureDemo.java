package com.bluesgao.threaddemo.future;

import java.util.concurrent.*;

public class CompletableFutureDemo {
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2,
            1, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(5),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<String> future = new CompletableFuture<>();
        POOL_EXECUTOR.execute(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("----" + Thread.currentThread().getName() + " set future result----");
            future.complete("hello, jiaduo");
        });

        System.out.println("-----main thread wait future result------");
        System.out.println(future.get());
        System.out.println("-----main thead got future result----------");
    }
}
