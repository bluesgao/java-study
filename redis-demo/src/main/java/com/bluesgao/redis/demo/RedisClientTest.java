package com.bluesgao.redis.demo;

import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

public class RedisClientTest {
    public static void main(String[] args) {
        String key = "user:1";
        Jedis jedisClient = new Jedis("47.97.205.190", 6379);
        jedisClient.auth("gx123456");

        System.out.printf("key:%s", jedisClient.get(key));

        try {
            TimeUnit.SECONDS.sleep(30L);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.printf("redis ping:%s", jedisClient.ping());

        try {
            TimeUnit.SECONDS.sleep(15L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        jedisClient.close();
    }
}
