package com.bluesgao.java.study.lock;

public class CLHLockMain {
    public static void main(String[] args) {
        Lock lock = new CLHLock();

        for(int i=0; i<100; i++){
            new Thread(new Worker(lock, i+"")).start();
        }
    }
}
