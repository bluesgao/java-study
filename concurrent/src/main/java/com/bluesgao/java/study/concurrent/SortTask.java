package com.bluesgao.java.study.concurrent;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

/**
 * ClassName: ForkJoinTask
 * Desc: 数组排序
 * Author: gaoxin11
 * Date: 2018/6/12 20:22
 **/
public class SortTask extends RecursiveTask<int[]> {
    private int source[];

    public SortTask(int source[]) {
        this.source = source;
    }

    @Override
    protected int[] compute() {
        int length = source.length;

        // 如果条件成立，说明任务中要进行排序的集合还不够小
        if (length > 2) {
            int middle = length / 2;

            // 拆分成两个子任务
            SortTask task1 = new SortTask(Arrays.copyOf(source, middle));
            task1.fork();

            SortTask task2 = new SortTask(Arrays.copyOfRange(source, middle, length));
            task2.fork();

            int[] result1 = task1.join();
            int[] result2 = task2.join();

            // 将两个有序的数组，合并成一个有序的数组
            int mer[] = joinInts(result1, result2);
            return mer;
        }
        // 否则说明集合中只有一个或者两个元素，可以进行这两个元素的比较排序了
        else {
            // 如果条件成立，说明数组中只有一个元素，或者是数组中的元素都已经排列好位置了
            if (length == 1
                    || source[0] <= source[1]) {
                return source;
            } else {
                int targetp[] = new int[length];
                targetp[0] = source[1];
                targetp[1] = source[0];
                return targetp;
            }
        }
    }

    /**
     * 这个方法用于合并两个有序集合
     *
     * @param array1
     * @param array2
     */
    private static int[] joinInts(int array1[], int array2[]) {
        int destInts[] = new int[array1.length + array2.length];
        int array1Len = array1.length;
        int array2Len = array2.length;
        int destLen = destInts.length;

        // 只需要以新的集合destInts的长度为标准，遍历一次即可
        for (int index = 0, array1Index = 0, array2Index = 0; index < destLen; index++) {
            int value1 = array1Index >= array1Len ? Integer.MAX_VALUE : array1[array1Index];
            int value2 = array2Index >= array2Len ? Integer.MAX_VALUE : array2[array2Index];
            // 如果条件成立，说明应该取数组array1中的值
            if (value1 < value2) {
                array1Index++;
                destInts[index] = value1;
            }
            // 否则取数组array2中的值
            else {
                array2Index++;
                destInts[index] = value2;
            }
        }
        return destInts;
    }
}
