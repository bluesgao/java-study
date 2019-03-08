package com.bluesgao.reactordemo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Acceptor implements Runnable {
    private int port;
    private Selector selector;
    private BlockingQueue<InputSource> sourceBlockingQueue = new LinkedBlockingQueue<>();

    public Acceptor(int port, Selector selector) {
        this.port = port;
        this.selector = selector;
    }

    public void addNewConnection(InputSource inputSource) {
        System.out.println(Thread.currentThread().getName()+" addNewConnection:" + inputSource.toString());
        sourceBlockingQueue.offer(inputSource);
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" Acceptor loop start");

        Thread conn = new Thread() {
            @Override
            public void run() {
                long i = 0;
                while (true) {
                    InputSource inputSource = new InputSource();
                    inputSource.setId(i);
                    inputSource.setData("input data:" + i);
                    addNewConnection(inputSource);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
            }
        };

        conn.setName("conn-Thread");
        conn.start();

        while (true) {
            InputSource inputSource = null;
            try {
                inputSource = sourceBlockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (inputSource != null) {
                Event event = new Event();
                event.setInputSource(inputSource);
                event.setEventType(EventType.ACCEPT);

                selector.addEvent(event);
            }
        }
    }

    public int getPort() {
        return port;
    }
}
