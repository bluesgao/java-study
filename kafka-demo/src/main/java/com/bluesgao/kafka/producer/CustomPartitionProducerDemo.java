package com.bluesgao.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/*
 * 自定义分区策略
 * 查看每个分区的消息数
 * bin/kafka-run-class.sh kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic audit-test
 */
public class CustomPartitionProducerDemo {
    public static void main(String[] args) {
        Properties props = new Properties();
        //分区策略实现类
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.bluesgao.kafka.producer.CustomPartitioner");

        String topic = "audit-test";
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        ProducerRecord nonKeyRecord = new ProducerRecord(topic, "non-key record");
        //这类消息需要放在最后一个分区
        ProducerRecord auditRecord = new ProducerRecord(topic, "audit", "audit record");
        ProducerRecord nonAuditRecord = new ProducerRecord(topic, "other", "non-audit record");

        try {
            producer.send(nonAuditRecord).get();
            producer.send(nonAuditRecord).get();
            producer.send(auditRecord).get();
            producer.send(nonAuditRecord).get();
            producer.send(nonAuditRecord).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
