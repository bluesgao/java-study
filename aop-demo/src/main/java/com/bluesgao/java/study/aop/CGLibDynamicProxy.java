package com.bluesgao.java.study.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * ClassName: CGLibDynamicProxy
 * Desc: TODO
 * Author: gaoxin11
 * Date: 2018/5/25 17:02
 **/
public class CGLibDynamicProxy implements MethodInterceptor {
    private static CGLibDynamicProxy instance = new CGLibDynamicProxy();

    public CGLibDynamicProxy() {
    }

    public static CGLibDynamicProxy getInstance(){
        return instance;
    }

    public <T> T getProxy(Class<T> cls){
        return (T)Enhancer.create(cls, this);
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        before();
        Object reslut = proxy.invokeSuper(obj, args);
        after();
        return reslut;
    }

    private void before(){
        System.out.println(JdkDynamicProxy.class.getSimpleName() +" before");
    }

    private void after(){
        System.out.println(JdkDynamicProxy.class.getSimpleName() +" after");
    }}
