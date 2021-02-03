package com.bluesgao.springdemo.ioc.customevent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * @ClassName：RegisterService
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/2 16:55
 **/
@Service
public class RegisterService implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void register(String userName) {
        System.out.println(userName + "注册成功");
        publisher.publishEvent(new RegisterSuccessEvent(userName));
    }
}
