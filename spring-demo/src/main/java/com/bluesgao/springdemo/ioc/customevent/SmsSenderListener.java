package com.bluesgao.springdemo.ioc.customevent;

import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName：SmsSenderListener
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/2 16:51
 **/
@Component
@Order(value = -1)

public class SmsSenderListener implements ApplicationListener<RegisterSuccessEvent> {

    @Override
    public void onApplicationEvent(RegisterSuccessEvent event) {
        System.out.println("监听到用户注册成功，发送短信。。。");
    }
}
