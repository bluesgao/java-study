package com.bluesgao.java.study.aop;

/**
 * ClassName: StaticProxy
 * Desc: 静态代理案例
 * Author: gaoxin11
 * Date: 2018/5/25 14:43
 **/
public class StaticProxy implements Hello {

    private HelloImpl hello;
    public StaticProxy(HelloImpl hello) {
        this.hello = hello;
    }

    public void hi(String name) {
       this.before();
       this.hello.hi(name);
       this.after();
    }

    private void before(){
        System.out.println("before");
    }
    private void after(){
        System.out.println("after");
    }
}
