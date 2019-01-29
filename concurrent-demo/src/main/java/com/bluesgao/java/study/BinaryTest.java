package com.bluesgao.java.study;

public class BinaryTest {
    public static void main(String[] args) {
        int COUNT_BITS = Integer.SIZE - 3;

        int RUNNING    = -1 << COUNT_BITS;
        int SHUTDOWN   =  0 << COUNT_BITS;
        int STOP       =  1 << COUNT_BITS;
        int TIDYING    =  2 << COUNT_BITS;
        int TERMINATED =  3 << COUNT_BITS;
        System.out.println(String.format("RUNNING:%d-%s", RUNNING, Integer.toBinaryString(RUNNING)));
        System.out.println(String.format("SHUTDOWN:%d-%s", SHUTDOWN, Integer.toBinaryString(SHUTDOWN)));
        System.out.println(String.format("STOP:%d-%s", STOP, Integer.toBinaryString(STOP)));
        System.out.println(String.format("TIDYING:%d-%s", TIDYING, Integer.toBinaryString(TIDYING)));
        System.out.println(String.format("TERMINATED:%d-%s", TERMINATED, Integer.toBinaryString(TERMINATED)));
    }
}
