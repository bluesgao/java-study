package com.bluesgao.java.study.future.inquiry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 询价
 */
public class InquiryService {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        InquiryService inquiryService = new InquiryService();
        List<Offer> offerList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            offerList.add(new Offer(i + 0L, new Random().nextInt(Integer.MAX_VALUE) + 0L));
        }
        List<Offer> offerResultList = inquiryService.inquiry(threadPool, offerList, 95, TimeUnit.MILLISECONDS);
        System.out.println("offerResultList:" + offerResultList.toString());

        threadPool.shutdown();
        System.out.println("threadPool.shutdown");


    }

    public List<Offer> inquiry(ExecutorService threadPool, List<Offer> offerList, long timeOut, TimeUnit timeUnit) {
        List<Future<Offer>> futureList = new ArrayList<>(offerList.size());
        //提交任务
        for (Offer o : offerList) {
            Future<Offer> future = threadPool.submit(new Callable<Offer>() {
                @Override
                public Offer call() throws Exception {
                    TimeUnit.MILLISECONDS.sleep(getRandom(90, 110));
                    return new Offer(o.getMerchantId(), o.getSkuId(), getRandom(1, 10000));
                }
            });
            futureList.add(future);
        }

        //获取结果
        List<Offer> offerResultList = new ArrayList<>(offerList.size());
        for (Future<Offer> offerFuture : futureList) {
            try {
                //在 get 的时候应当使用超时限制
                offerResultList.add(offerFuture.get(timeOut, timeUnit));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
                System.out.println("Timeout:" + offerFuture.toString());
            }
        }
        return offerResultList;
    }

    public static int getRandom(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;

    }

}


