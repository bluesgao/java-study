package com.bluesgao.java.study.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量的一个最主要的作用就是，来控制那些需要限制并发访问量的资源。
 * 具体来讲，信号量会维护“许可证”的计数，而线程去访问共享资源前，必须先拿到许可证。
 * 线程可以从信号量中去“获取”一个许可证，一旦线程获取之后，信号量持有的许可证就转移过去了，所以信号量手中剩余的许可证要减一。
 * <p>
 * <p>
 * 场景：
 * 当下游服务可以处理的请求数量有限时，如果不控制请求数量，保护下游服务，会导致下游服务不可用
 */
public class SemaphoreDemo {
    //信号量-许可证数量3，非公平策略
    static Semaphore sema = new Semaphore(1);

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 100; i++) {
            es.submit(new SlowTask(i));
        }

        es.shutdown();
        System.out.println("---结束---");
    }

    static class SlowTask implements Runnable {
        private Integer id;

        public SlowTask(Integer id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                //获取许可证
                sema.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //调用慢任务
            System.out.println(Thread.currentThread().getName() + "拿到许可证,调用慢服务开始" + id);

            try {
                TimeUnit.MILLISECONDS.sleep(1000 * 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("--调用慢服务结束,释放许可证" + Thread.currentThread().getName() + ":" + id);

            //释放许可证
            sema.release();
        }
    }
}
