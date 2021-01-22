package com.bluesgao.ssm.demo.service;

import com.alibaba.fastjson.JSON;
import com.bluesgao.ssm.demo.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;
    @Test
    public void deposit() {

        int workNum = 40;
        CountDownLatch countDownLatch = new CountDownLatch(workNum);
        ExecutorService executorService = Executors.newFixedThreadPool(workNum);
        for (int i=0; i<workNum; i++){
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        doWork();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private void doWork(){
        Boolean ret = accountService.deposit(1,1);
        log.info("存款 输入:{1,1},结果:{}",ret);
    }

    @Test
    public void draw() {
    }
}