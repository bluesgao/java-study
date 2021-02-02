package com.bluesgao.springdemo.customevent;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName：EmailSenderListener
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/2 16:52
 **/
@Component
public class EmailSenderListener {
    @Order(value = -2)
    @EventListener
    public void onEvent(RegisterSuccessEvent event) {
        System.out.println("监听到用户注册成功！发送邮件。。。");
    }
}
