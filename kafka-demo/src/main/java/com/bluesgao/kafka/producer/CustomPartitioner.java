package com.bluesgao.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * * 实现一个自定义分区策略：
 * *
 * * key含有audit的一部分消息发送到最后一个分区上，其他消息在其他分区随机分配
 */
@Slf4j
public class CustomPartitioner implements Partitioner {
    private Random random;

    @Override
    public void configure(Map<String, ?> configs) {
        //做必要的初始化工作
        random = new Random();
    }

    //分区策略
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {

        String keyObj = (String) key;
        List<PartitionInfo> partitionInfoList = cluster.availablePartitionsForTopic(topic);
        int partitionCount = partitionInfoList.size();
        System.out.println("partition size: " + partitionCount);
        int auditPartition = partitionCount - 1;
        return keyObj == null || "".equals(keyObj) || !keyObj.contains("audit") ? random.nextInt(partitionCount - 1) : auditPartition;
    }

    @Override
    public void close() {
        //清理工作
    }
}
