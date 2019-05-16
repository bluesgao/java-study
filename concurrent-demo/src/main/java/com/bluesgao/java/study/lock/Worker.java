package com.bluesgao.java.study.lock;

public class Worker implements Runnable {
    private Lock lock;
    private String taskId;

    public Worker(Lock lock, String taskId) {
        this.lock = lock;
        this.taskId = taskId;
    }

    public void run() {
        try {
            lock.lock();
            Thread.sleep(5000);
            System.out.println(String.format("Thread %s completed.", taskId));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
