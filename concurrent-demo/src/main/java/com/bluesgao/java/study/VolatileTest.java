package com.bluesgao.java.study;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * （解决缓存一致性问题）MESI协议保证了每个缓存中使用的共享变量的副本是一致的。
 * 它核心的思想是：当CPU写数据时，如果发现操作的变量是共享变量，即在其他CPU中也存在该变量的副本，会发出信号通知其他CPU将该变量的缓存行置为无效状态，
 * 因此当其他CPU需要读取这个变量时，发现自己缓存中缓存该变量的缓存行是无效的，那么它就会从内存重新读取。
 *
 *
 * 1，volatile变量提供可见性保证。（可见性保证每次读取的数据都是最新的）
 * 2，对其修饰变量的单个操作提供原子性保证。（注意：复合操作（getAndSet）不保证原子性）
 * 3，为什么volatile变量对复合操作不保证原子性？
 *  分析：
 *      JMM保证每个线程在运行过程中都有自己的工作内存。
 *      a,假设线程1需要对volatile变量进行复合操作（先读后写），首先线程1会将volatile变量的值强制从主内存中读到自己的工作内存中，然后对volatile变量进行写操作，volatile变量会被强制写到主内存中。
 *      b,假设线程2在线程1读操作之后，写操作之前，读取了volatile变量的值，此时由于线程1对volatile变量的写操作还未提交到主内存，也就是说线程2此时读取的是线程1修改前的值，此时volatile变量的值就与预期不一样。
 *      c,原因在于线程1的读操作和写操作不是原子性。
 *      d,解决方案:保证读操作与写操作的原子性。使用synchronized、lock、AtomicInteger
 * 4，volatile内存语义：1，可见性（缓存一致性）；2，禁止指令重排（内存屏障）保证有序性。
 *
 */
public class VolatileTest {
    public volatile long i = 0L;

    Lock lock = new ReentrantLock();

    public void getAndIncr() {
        //先读后写（复合操作）
        //自增操作是不具备原子性的，它包括读取变量的原始值、进行加1操作、写入工作内存
        i++;
    }

    //使用synchronized保证复合操作（读操作与写操作）的原子性
    public synchronized void getAndIncr2() {
        i++;
    }

    //使用lock保证复合操作（读操作与写操作）的原子性
    public void getAndIncr3() {
        lock.lock();
        try {
            i++;
        } finally {
            lock.unlock();
        }
    }

    public void set(long j) {
        i = j;
    }

    public static void main(String[] args) {

        final VolatileTest volatileTest = new VolatileTest();

        for (int i = 0; i < 10; i++) {
            new Thread(){
                public void run(){
                    for (int j=0; j<1000; j++){
                        volatileTest.getAndIncr();
                    }
                }
            }.start();
        }

/*        while (Thread.activeCount()>2){
            System.out.println("activeCount=" + Thread.activeCount());
            Thread.yield();
        }*/

        //保证前面的线程都执行完
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //预期结果是：10*1000
        //实际结果是：9999，9991...
        System.out.println("i=" + volatileTest.i);
    }
}
