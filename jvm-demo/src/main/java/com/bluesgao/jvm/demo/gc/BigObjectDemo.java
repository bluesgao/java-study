package com.bluesgao.jvm.demo.gc;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName：BigObjectDemo
 * @Description：该案例主要测试 大对象的分配策略
 * @Author：bluesgao
 * @Date：2021/3/8 10:16
 * 知识点：大对象直接分配到老年代
 * 涉及jvm参数：-XX:MaxTenuringThreshold
 * ①设置分配到新生代对象的大小限制。默认值是0，表示任何对象首先会在新生代分配,
 * ②大于这个值的大对象(数组和字符串)直接在老年代分配（目的是避免在Eden区和两个Survivor区之间发生多次的内存复制）
 * JVM OPS:-server -Xms20m -Xmx20m -XX:+PrintCommandLineFlags -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintHeapAtGC -XX:+PrintGCTimeStamps
 **/
public class BigObjectDemo {
    public static void main(String[] args) throws Exception {
        //第一次：-XX:PretenureSizeThreshold=0 默认即可
        /**
         * -XX:+HeapDumpOnOutOfMemoryError -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:MaxNewSize=6991872 -XX:MaxTenuringThreshold=6 -XX:NewSize=6991872 -XX:OldPLABSize=16 -XX:OldSize=13979648 -XX:PretenureSizeThreshold=0 -XX:+PrintCommandLineFlags -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+UseParNewGC
         * Code Cache  总量:2555904   使用的内存:1145408
         * Metaspace  总量:4980736   使用的内存:3473744
         * Compressed Class Space  总量:524288   使用的内存:382984
         * Par Eden Space  总量:5636096   使用的内存:4306312
         * Par Survivor Space  总量:655360   使用的内存:0
         * CMS Old Gen  总量:14024704   使用的内存:0
         */
        //第二次：需在jvm参数上添加 -XX:PretenureSizeThreshold=1048576（1M）
        /**
         * -XX:+HeapDumpOnOutOfMemoryError -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:MaxNewSize=6991872 -XX:MaxTenuringThreshold=6 -XX:NewSize=6991872 -XX:OldPLABSize=16 -XX:OldSize=13979648 -XX:PretenureSizeThreshold=1048576 -XX:+PrintCommandLineFlags -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+UseParNewGC
         * Code Cache  总量:2555904   使用的内存:1138752
         * Metaspace  总量:4980736   使用的内存:3478552
         * Compressed Class Space  总量:524288   使用的内存:383632
         * Par Eden Space  总量:5636096   使用的内存:2218312
         * Par Survivor Space  总量:655360   使用的内存:0
         * CMS Old Gen  总量:14024704   使用的内存:2097168
         */
        bigArr();

        //waitingForSeconds(60*2);
    }

    private static void bigArr() {
        //4M
        byte[] arr = new byte[4 * 1024 * 1024];//1048576*2
        //通过jmx接口，输出jvm内存信息
        for (MemoryPoolMXBean memoryPoolMXBean : ManagementFactory.getMemoryPoolMXBeans()) {
            System.out.println(memoryPoolMXBean.getName() + "  总量:" + memoryPoolMXBean.getUsage().getCommitted() + "   使用的内存:" + memoryPoolMXBean.getUsage().getUsed());
        }
    }

    private static void waitingForSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void perSecends() throws Exception {
        int n = 0;
        while (true) {
            //每秒钟分配一个1m大小的对象
            byte[] bigObj = new byte[1024 * 1024 * 1];//1m的大对象
            n++;
            System.out.println("第" + n + "次分配");
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
