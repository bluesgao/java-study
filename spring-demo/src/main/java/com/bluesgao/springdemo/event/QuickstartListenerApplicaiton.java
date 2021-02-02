package com.bluesgao.springdemo.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName：QuickstartListenerApplicaiton
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/2 16:08
 **/
public class QuickstartListenerApplicaiton {
    public static void main(String[] args) throws Exception {
        System.out.println("准备初始化IOC容器");
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("com.bluesgao.springdemo.event");
        System.out.println("IOC容器初始化完成:" + ctx);

        TimeUnit.SECONDS.sleep(3);

        System.out.println("准备IOC容器关闭");
        ctx.close();
        System.out.println("IOC容器关闭");
    }
}
