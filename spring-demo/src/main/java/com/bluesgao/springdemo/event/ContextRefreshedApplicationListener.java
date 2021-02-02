package com.bluesgao.springdemo.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @ClassName：ContextRefreshedApplicationListener
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/2 16:05
 **/
@Component
public class ContextRefreshedApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("ContextRefreshedApplicationListener监听到ContextRefreshedEvent事件！");
    }
}
