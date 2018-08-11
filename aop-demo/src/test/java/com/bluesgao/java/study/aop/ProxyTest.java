package com.bluesgao.java.study.aop;

import org.junit.Before;
import org.junit.Test;

public class ProxyTest {

    private static void staticProxyTest() {
        StaticProxy staticProxy = new StaticProxy(new HelloImpl());
        staticProxy.hi("blues");
    }

    private static void jdkDynamicProxyTest() {
        Hello hello = new JdkDynamicProxy(new HelloImpl()).getProxy();
        hello.hi("jack");
    }

    private static void cglibDynamicProxyTest(){
        Hello hello = CGLibDynamicProxy.getInstance().getProxy(HelloImpl.class);
        hello.hi("gao");
    }

    public static void main(String[] args) {
        //JDK动态代理时生成class文件
        //设置运行环境变量,运行后会把class文件生成在classpath目录下
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");

        //jdkDynamicProxyTest();
        cglibDynamicProxyTest();
    }
}