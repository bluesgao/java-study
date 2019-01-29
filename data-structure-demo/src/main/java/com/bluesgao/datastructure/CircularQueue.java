package com.bluesgao.datastructure;

import java.util.Random;

/**
 * 循环队列
 */
public class CircularQueue<E> {

    /**
     * 默认容量
     */
    private static final int DEFAULT_CAPACITY = 16;

    /**
     * 容量
     */
    private int capacity;

    /**
     * 队头
     */
    private int head;

    /**
     * 对尾
     */
    private int tail;

    /**
     * 定义一个数组用于保存循环队列中的元素
     */
    private Object[] elements;

    public CircularQueue(int capacity) {
        this.capacity = capacity;
        elements = new Object[capacity];
        this.head = 0;
        this.tail = 0;
    }

    public CircularQueue() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * 循环队列中元素的个数
     *
     * @return
     */
    public int size() {
        if (tail > head) {
            return tail - head;
        } else {
            return capacity - (head - tail);
        }
    }

    /**
     * 判断是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return head == tail;
    }

    /**
     * 判断是否已满
     *
     * @return
     */
    public boolean isFull() {
        return head == (tail + 1) % capacity;
    }

    /**
     * 入队
     * 循环队列入队不受限制，入队比出队快时，存在数据覆盖问题
     *
     * @param e
     * @return
     */
    public boolean enqueue(E e) {
        /*if (isFull()) {
            return false;
        }*/

        elements[tail] = e;
        //如果tail已经到头，那就转头
        tail = (tail + 1) % capacity;

        return true;
    }

    /**
     * 出队
     *
     * @return
     */
    public E dequeue() {
        if (isEmpty()) {
            return null;
        } else {
            E e = (E) elements[head];
            //如果head已经到头，那就转头
            head = (head + 1) % capacity;
            return e;
        }
    }


    public static void main(String[] args) {
        final CircularQueue<String> circularQueue = new CircularQueue<String>();
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 32; i++) {
                    boolean res = circularQueue.enqueue("test-" + i);
                    System.out.println("入队:" + Thread.currentThread().getName() + ":" + i + ":" + "test-" + i);
                    try {
                        Thread.sleep(new Random().nextInt(200));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 40; i++) {
                    try {
                        Thread.sleep(new Random().nextInt(200));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("出队:" + Thread.currentThread().getName() + ":" + i + ":" + circularQueue.dequeue());
                }
            }
        }).start();
        System.out.println(circularQueue);
    }
}
