package com.bluesgao.reactordemo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Selector {
    private BlockingQueue<Event> eventBlockingQueue = new LinkedBlockingQueue<>();
    private Object lock = new Object();

    public void addEvent(Event event) {
        System.out.println(Thread.currentThread().getName()+" Selector addEvent:"+event.toString());
        boolean flag = eventBlockingQueue.offer(event);
        if (flag) {
            synchronized (lock) {
                lock.notify();
            }
        }
    }

    public List<Event> select(long timeout) {
        if (timeout > 0) {
            if (eventBlockingQueue.isEmpty()) {
                synchronized (lock) {
                    if (eventBlockingQueue.isEmpty()) {
                        try {
                            lock.wait(timeout);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        List<Event> events = new ArrayList<>();
        eventBlockingQueue.drainTo(events);
        return events;
    }

    public List<Event> select() {
        return select(0);
    }
}
