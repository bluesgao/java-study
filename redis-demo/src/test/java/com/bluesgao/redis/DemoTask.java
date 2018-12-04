package com.bluesgao.redis;

import com.bluesgao.redis.demo.RedisUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

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
    private static final int cmdCount = 1000;

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

    /**
     * 不使用pipeline的批量操作
     *
     * @param jedis
     */
    private void runWithNonPipeline(Jedis jedis) {
        try {
            for (int i = 0; i < cmdCount; i++) {
                String key = key(i);
                String value = UUID.randomUUID().toString();
                jedis.set(key, value);
                log.info("normal-Task[{}]-key[{}]-value[{}]", Thread.currentThread().getName(), key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 使用pipeline的批量操作
     *
     * @param jedis
     */
    private void runWithPipeline(Jedis jedis) {
        int j = 0;

        try {
            Pipeline pipeline = jedis.pipelined();
            for (int i = 0; i < cmdCount; i++) {
                String key = key(i);
                String value = UUID.randomUUID().toString();
                pipeline.set(key, value);

                //20条命令提交一次，防止一次提交太多命令
                if (i % 20 == 0) {
                    pipeline.sync();
                    log.info("pipeline-Task[{}]-flush-[{}] ", Thread.currentThread().getName(), ++j);
                }
            }
            //flush output stream 提交tcp sendbuffer中的命令数据到server端
            pipeline.sync();
            log.info("pipeline-Task[{}]-flush ", Thread.currentThread().getName());
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
