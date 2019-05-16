package com.bluesgao.jvm.demo;

public class TryCatchFinallyDemo {
    public static int foo1() {
        try {
            int a = 1 / 0;
            return 0;
        } catch (Exception e) {
            return 1;
        } finally {
            return 2;
        }
    }

    public void foo2() {
        String s1 = "1";
        try {
            s1 = "222";
        } finally {
            s1 = "333";
        }
    }

    public static void main(String[] args) {
        System.out.println(foo1());
    }
}
