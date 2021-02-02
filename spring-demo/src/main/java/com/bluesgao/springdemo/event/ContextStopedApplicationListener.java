package com.bluesgao.springdemo.event;

import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;

/**
 * @ClassName：ContextStopedApplicationListener
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/2 16:35
 **/
public class ContextStopedApplicationListener {
    @EventListener
    public void onContextClosedEvent(ContextStoppedEvent event) {
        System.out.println("ContextStopedApplicationListener监听到ContextStoppedEvent事件！");
    }
}
