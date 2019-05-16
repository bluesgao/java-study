package com.bluesgao.java.study.lock;

import java.util.concurrent.atomic.AtomicReference;

public class CLHLock implements Lock {
    private volatile AtomicReference<Node> tail;
    private volatile ThreadLocal<Node> threadLocal;

    public CLHLock() {
        this.tail = new AtomicReference<Node>();
        this.threadLocal = new ThreadLocal<Node>();
    }

    public void unlock() {
        Node curNode = threadLocal.get();
        threadLocal.remove();
        if (curNode == null || curNode.isLocked() == false) {
            return;
        }

        if (!tail.compareAndSet(curNode, null)) {
            curNode.setLocked(false);
        }
    }

    public void lock() {
        Node curNode = threadLocal.get();
        if (curNode == null) {
            curNode = new Node();
            threadLocal.set(curNode);
        }

        Node prevNode = tail.getAndSet(curNode);
        if (prevNode != null) {
            for (; ; ) {
                if (prevNode.isLocked()) {
                    return;
                }
            }
        }
    }

    private static class Node {
        private volatile boolean locked = true;

        public boolean isLocked() {
            return locked;
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }
    }
}
