package com.bluesgao.jvm.demo;

/**
 * @ClassName：ClassLoaderDemo
 * @Description：
 * @Author：bluesgao
 * @Date：2021/3/5 17:07
 **/
public class ClassLoaderDemo {
    public static void main(String[] args) {
        System.out.println(Class.class.getClassLoader());
        try {
            Class forTestClass = Class.forName("com.bluesgao.jvm.demo.ForTest");
            System.out.println(forTestClass.getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
