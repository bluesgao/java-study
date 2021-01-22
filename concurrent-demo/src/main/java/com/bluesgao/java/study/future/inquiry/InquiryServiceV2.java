package com.bluesgao.java.study.future.inquiry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 询价
 */
public class InquiryServiceV2 {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(5);

        InquiryServiceV2 inquiryService = new InquiryServiceV2();

        //构造被询价方
        List<Offer> offerList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            offerList.add(new Offer(i + 0L, new Random().nextInt(Integer.MAX_VALUE) + 0L));
        }

        long start = System.currentTimeMillis();
        List<Offer> offerResultList = inquiryService.inquiry(es, new CountDownLatch(offerList.size()), offerList, 200);
        System.out.println("offerResultList:" + offerResultList.toString());

        es.shutdown();
        System.out.println("expend time:" + (System.currentTimeMillis() - start));


    }

    public List<Offer> inquiry(ExecutorService threadPool, CountDownLatch latch, List<Offer> offerList, long deadline) {
        //提交任务
        for (Offer o : offerList) {
            threadPool.submit(new Task(o, latch));
        }
        try {
            latch.await(deadline, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return offerList;
    }

    private class Task implements Runnable {
        private Offer offer;
        private CountDownLatch latch;

        public Task(Offer offer, CountDownLatch latch) {
            this.offer = offer;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                //模拟第三方延时返回数据
                TimeUnit.MILLISECONDS.sleep(getRandom(20, 50) * offer.getMerchantId());
                offer.setPrice(getRandom(1, 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }

    public static int getRandom(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }
}


