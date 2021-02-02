package com.bluesgao.springdemo.event;

import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName：ContextClosedApplicationListener
 * @Description：注解式监听器
 * @Author：bluesgao
 * @Date：2021/2/2 16:18
 **/
@Component
public class ContextClosedApplicationListener {
    @EventListener
    public void onContextClosedEvent(ContextClosedEvent event) {
        System.out.println("ContextClosedApplicationListener监听到ContextClosedEvent事件！");
        System.out.println("ContextClosedApplicationListener ctx:" + event.getApplicationContext());
    }
}
