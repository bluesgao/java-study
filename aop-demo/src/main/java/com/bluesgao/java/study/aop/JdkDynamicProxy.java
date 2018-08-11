package com.bluesgao.java.study.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ClassName: JdkDynamicProxy
 * Desc: TODO
 * Author: gaoxin11
 * Date: 2018/5/25 16:16
 **/
public class JdkDynamicProxy implements InvocationHandler {
    private Object target;

    public JdkDynamicProxy(Object target){
        this.target = target;
    }

    public <T> T getProxy(){
        return (T)Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target, args);
        after();
        return result;
    }


    private void before(){
        System.out.println(JdkDynamicProxy.class.getSimpleName() +" before");
    }

    private void after(){
        System.out.println(JdkDynamicProxy.class.getSimpleName() +" after");
    }
}
