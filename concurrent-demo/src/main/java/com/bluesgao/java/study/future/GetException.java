package com.bluesgao.java.study.future;

import java.util.concurrent.*;

public class GetException {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(20);
        Future<Integer> f = es.submit(new Task());

        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("i:" + i);
                TimeUnit.MILLISECONDS.sleep(500);
            }

            //isDone() 方法：判断是否执行完毕
            //如果返回 true 则代表执行完成了；如果返回 false 则代表还没完成
            // isDone 方法在返回 true 的时候，不代表这个任务是成功执行的，只代表它执行完毕了
            //注意：
            //1,即便任务抛出异常，isDone 方法依然会返回 true
            //2,虽然抛出的异常是 IllegalArgumentException，但是对于 get 而言，它抛出的异常依然是 ExecutionException
            //3.虽然在任务执行一开始时就抛出了异常，但是真正要等到我们执行 get 的时候，才看到了异常
            System.out.println(f.isDone());
            f.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            throw new IllegalArgumentException("参数错误");
        }
    }
}
