package com.bluesgao.threaddemo.base;

import java.util.concurrent.TimeUnit;


/**
 * 中断是通过调用Thread.interrupt()方法来做的.
 * 这个方法通过修改了被调用线程的中断状态来告知那个线程, 说它被中断了.
 * 对于非阻塞中的线程, 只是改变了中断状态, 即Thread.isInterrupted()将返回true;
 * 对于可取消的阻塞状态中的线程, 比如等待在这些函数上的线程, Thread.sleep(), Object.wait(), Thread.join(),
 * 这个线程收到中断信号后, 会抛出InterruptedException, 同时会把中断状态置回为true.
 * 但调用Thread.interrupted()会对中断状态进行复位
 * <p>
 * 没有占用CPU运行的线程是不可能给自己的中断状态置位的，这就会产生一个InterruptedException异常。
 */
public class StopThread implements Runnable {
    @Override
    public void run() {
        try {
            int count = 0;
            while (!Thread.currentThread().isInterrupted() && count < 1000) {
                System.out.println("count=" + count++);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new StopThread());
        t.start();

        TimeUnit.SECONDS.sleep(5);

        //给线程一个中断信号, 让它自己决定该怎么办
        t.interrupt();
    }
}
