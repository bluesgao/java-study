package com.bluesgao.redis;

import com.alibaba.fastjson.JSON;
import com.bluesgao.redis.demo.RedisUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Data
public class DemoTask implements Runnable {
    private CountDownLatch latch;
    private boolean usePipeline;

    // pipeline大小
    private static final int batchSize = 10;
    // 每个任务处理命令数
    private static final int cmdCount = 100;

    public DemoTask(CountDownLatch latch, boolean usePipeline) {
        this.latch = latch;
        this.usePipeline = usePipeline;
    }

    public void run() {

        Jedis jedis = RedisUtils.getJedisClient();

        if (jedis != null) {
            log.info("Task[{}] start.", Thread.currentThread().getName());
            try {
                if (usePipeline) {
                    runWithPipeline(jedis);
                } else {
                    runWithNonPipeline(jedis);
                }
            } finally {
                latch.countDown();
            }

            log.info("Task[{}] end.", Thread.currentThread().getName());
        }
    }

    private void runWithNonPipeline(Jedis jedis) {
        try {
            for (int i = 0; i < cmdCount; i++) {
                String key = key(i);
                String value = UUID.randomUUID().toString();
                jedis.set(key, value);
                log.info("Task[{}]-key[{}]-value[{}]", Thread.currentThread().getName(), key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    private void runWithPipeline(Jedis jedis) {
        try {
            Pipeline pipeline = jedis.pipelined();
            for (int i = 0; i < cmdCount; i++) {
                String key = key(i);
                String value = UUID.randomUUID().toString();
                pipeline.set(key, value);
                Response<String> response = pipeline.get(key);
                log.info("pipeline-Task[{}]-key[{}]-value[{}] ", Thread.currentThread().getName(), key, JSON.toJSON(response));
            }
            //pipeline.sync();
        } catch (
                Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    private String key(int i) {
        return "pipeline:" + Thread.currentThread().getName() + ":" + i;
    }
}
