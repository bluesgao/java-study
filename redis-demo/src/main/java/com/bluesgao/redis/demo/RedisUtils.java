package com.bluesgao.redis.demo;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.*;
import java.util.Properties;

@Slf4j
public class RedisUtils {
    private static JedisPool jedisPool;

    static {
        InputStream is = null;

        try {
            //读取配置文件
            is = new BufferedInputStream(new FileInputStream("D:\\study\\code\\java-study\\redis-demo\\src\\main\\resources\\redis.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Properties p = null;
        if (is != null) {
            try {
                p = new Properties();
                p.load(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        log.info("RedisUtils p:{}", JSON.toJSONString(p));

        if(p != null){
            try {
                //设置jedispool配置
                JedisPoolConfig jpc = new JedisPoolConfig();
                jpc.setMaxTotal(Integer.parseInt(p.getProperty("redis.pool.maxTotal")));
                jpc.setMaxIdle(Integer.parseInt(p.getProperty("redis.pool.maxIdle")));
                jpc.setMinIdle(Integer.parseInt(p.getProperty("redis.pool.minIdle")));
                jpc.setMaxWaitMillis(Integer.parseInt(p.getProperty("redis.pool.maxWaitMillis")));

                log.info("RedisUtils jpc:{}", JSON.toJSONString(jpc));

                //jedisPool=new JedisPool(config,ADDR,PORT,TIMEOUT,AUTH);
                //poolConfig, host, port, timeout, timeout, password, database
                String host = p.getProperty("redis.host");
                Integer port = Integer.parseInt(p.getProperty("redis.port"));
                Integer timeout = Integer.parseInt(p.getProperty("redis.timeout"));
                String password = p.getProperty("redis.password");

                //建立jedis连接池
                jedisPool = new JedisPool(jpc, host, port, timeout, password);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从jedispool中获取一个redis客户端连接
     *
     * @return
     */
    public static synchronized Jedis getJedisClient() {
        if (jedisPool == null) {
            return null;
        }
        return jedisPool.getResource();
    }
}
