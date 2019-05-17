package com.bluesgao.jvm.demo;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleTest {
    public void hi(String s) {
        System.out.println("hello " + s);
    }

    public static void main(String[] args) {
        MethodHandleTest methodHandleTest = new MethodHandleTest();
        MethodType methodType = MethodType.methodType(void.class, String.class);
        MethodHandle methodHandle = null;
        try {
            methodHandle = MethodHandles.lookup().findVirtual(MethodHandleTest.class,"hi",methodType);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            methodHandle.invokeExact(methodHandleTest, "gx");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
