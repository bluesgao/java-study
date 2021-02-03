package com.bluesgao.springdemo.ioc.event;

import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName：ContextStartedApplicationListener
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/2 16:05
 **/
@Component
public class ContextStartedApplicationListener {
    @EventListener
    public void onApplicationEvent(ContextStartedEvent event) {
        System.out.println("ContextStartedApplicationListener监听到ContextStartedEvent事件！");
    }
}
