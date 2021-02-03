package com.bluesgao.springdemo.ioc.customevent;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName：WebSiteSenderListener
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/2 16:53
 **/
@Component
public class WebSiteSenderListener {
    @Order(value = 0)
    @EventListener
    public void onEvent(RegisterSuccessEvent event) {
        System.out.println("监听到用户注册成功，发送站内信。。。");
    }
}
