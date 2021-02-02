package com.bluesgao.springdemo.customevent;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName：App
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/2 16:54
 **/
public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("com.bluesgao.springdemo.customevent");
        RegisterService registerService = ctx.getBean(RegisterService.class);
        registerService.register("bluesgao");
    }
}
