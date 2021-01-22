package com.bluesgao.threaddemo.future;

import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class RunAsyncDemo {
    private final static int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private final static ThreadPoolExecutor BIZ_POOL_EXECUTOR = new ThreadPoolExecutor(AVAILABLE_PROCESSORS,
            AVAILABLE_PROCESSORS * 2,
            1, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(10),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) {
        try {
            //runAsyncWithBizExecutor();
//            supplyAsync();
//            supplyAsyncWithBizExecutor();
//            thenRun();
//            thenAccept();
//            thenApply();
            whenComplete();

            // 3.挂起当前线程，等待异步任务执行完毕
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //没有返回值的异步执行，异步任务由业务自己的线程池执行”
    public static void runAsyncWithBizExecutor() throws InterruptedException, ExecutionException {
        // 1.1创建异步任务，并返回future
        CompletableFuture future = CompletableFuture.runAsync(new Runnable() {

            @Override
            public void run() {
                // 1.1.1休眠2s模拟任务计算
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("over");
            }
        }, BIZ_POOL_EXECUTOR);

        // 1.2 同步等待异步任务执行结束
        System.out.println(future.get());
    }

    // 2. 有返回值的异步执行
    public static void supplyAsync() throws InterruptedException, ExecutionException {
        // 2.1创建异步任务，并返回future
        CompletableFuture future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                // 2.1.1休眠2s模拟任务计算
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // 2.1.2 返回异步计算结果
                return "hello,jiaduo";
            }
        });

        // 2.2 同步等待异步任务执行结束
        System.out.println(future.get());
    }


    // 2. 有返回值的异步执行
    public static void supplyAsyncWithBizExecutor() throws InterruptedException, ExecutionException {
        // 2.1创建异步任务，并返回future
        CompletableFuture future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                // 2.1.1休眠2s模拟任务计算
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // 2.1.2 返回异步计算结果
                return "hello,jiaduo-666";
            }
        }, BIZ_POOL_EXECUTOR);

        // 2.2 同步等待异步任务执行结束
        System.out.println(future.get());
    }

    // 3. thenRun不能访问oneFuture的结果
    public static void thenRun() throws InterruptedException, ExecutionException {
        // 1.创建异步任务，并返回future
        CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(new Supplier<String>() {

            @Override
            public String get() {
                // 1.1休眠2s，模拟任务计算
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 1.2返回计算结果
                return "hello";
            }
        });
        // 2.在future上施加事件，当future计算完成后回调该事件，并返回新future
        CompletableFuture twoFuture = oneFuture.thenRun(new Runnable() {

            @Override
            public void run() {
                // 2.1.1当oneFuture任务计算完成后做一件事情
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
                System.out.println("---after oneFuture over doSomething---");
            }
        });

        // 3.同步等待twoFuture对应的任务完成，返回结果固定为null
        System.out.println(twoFuture.get());

    }

    //4. thenAccept基于thenAccept实现异步任务A，执行完毕后，激活异步任务B执行，
    // 需要注意的是，这种方式激活的异步任务B是可以拿到任务A的执行结果的
    public static void thenAccept() throws InterruptedException, ExecutionException {
        // 1.创建异步任务，并返回future
        CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                // 1.1休眠2s，模拟任务计算
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 1.2返回计算结果
                return "hello";
            }
        });
        // 2.在future上施加事件，当future计算完成后回调该事件，并返回新future
        CompletableFuture twoFuture = oneFuture.thenAccept(new Consumer<String>() {

            @Override
            public void accept(String t) {
                // 2.1.1对oneFuture返回的结果进行加工
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("---after oneFuture over doSomething---" + t);
            }
        });

        // 3.同步等待twoFuture对应的任务完成，返回结果固定为null
        System.out.println(twoFuture.get());
    }

    //5.基于thenApply实现异步任务A，执行完毕后，激活异步任务B执行。
    // 需要注意的是，这种方式激活的异步任务B是可以拿到任务A的执行结果的，并且可以获取到异步任务B的执行结果
    public static void thenApply() throws InterruptedException, ExecutionException {
        // 1.创建异步任务，并返回future
        CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(new Supplier<String>() {

            @Override
            public String get() {

                // 1.1休眠2s，模拟任务计算
                try {
                    Thread.sleep(1100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 1.2返回计算结果
                return "hello";
            }
        });

        // 2.在future上施加事件，当future计算完成后回调该事件，并返回新future
        CompletableFuture<String> twoFuture = oneFuture.thenApply(new Function<String, String>() {

            // 2.1在步骤1计算结果基础上进行计算，这里t为步骤1返回的hello
            @Override
            public String apply(String t) {
                // 2.1.1对oneFuture返回的结果进行加工
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 2.1.2返回加工后结果
                return t + " jiduo";
            }
        });

        // 3.同步等待twoFuture对应的任务完成，并获取结果
        System.out.println(twoFuture.get());
    }

    //6.基于whenComplete设置回调函数，当异步任务执行完毕后进行回调，不会阻塞调用线程
    public static void whenComplete() throws InterruptedException, ExecutionException, TimeoutException {

        // 1.创建一个CompletableFuture对象
        CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {

            @Override
            public String get() {
                // 1.1模拟异步任务执行
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 1.2返回计算结果
                return "hello,jiaduo";
            }
        });

        // 2.添加回调函数
        future.whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String t, Throwable u) {
                // 2.1如果没有异常，打印异步任务结果
                if (null == u) {
                    System.out.println(Thread.currentThread().getName()+" whenComplete:" + t);
                } else {
                    // 2.2打印异常信息
                    System.out.println(u.getLocalizedMessage());
                }
            }
        });
    }
}

