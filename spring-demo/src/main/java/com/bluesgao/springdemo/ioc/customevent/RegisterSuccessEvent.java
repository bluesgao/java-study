package com.bluesgao.springdemo.ioc.customevent;

import org.springframework.context.ApplicationEvent;

/**
 * @ClassName：RegisterSuccessEvent
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/2 16:50
 **/
public class RegisterSuccessEvent extends ApplicationEvent {
    public RegisterSuccessEvent(Object source) {
        super(source);
    }
}
