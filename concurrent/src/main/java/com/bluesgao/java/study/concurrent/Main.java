package com.bluesgao.java.study.concurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * ClassName: Main
 * Desc: TODO
 * Author: gaoxin11
 * Date: 2018/6/12 20:36
 **/
public class Main {
    private static int MAX = 10000;

    private static int inits[] = new int[MAX];

    // 这是为了生成一个数量为MAX的随机整数集合，准备计算数据
    // 和算法本身并没有什么关系
    static {
        Random r = new Random();
        for (int index = 1; index <= MAX; index++) {
            inits[index - 1] = r.nextInt(10000000);
        }
    }

    public static void main(String[] args) throws Exception {
        // 正式开始
        long beginTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        SortTask task = new SortTask(inits);
        ForkJoinTask<int[]> taskResult = pool.submit(task);
        try {
            List list = Arrays.asList(taskResult.get());
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时=" + (endTime - beginTime));
        System.out.println(pool.toString());
    }
}
