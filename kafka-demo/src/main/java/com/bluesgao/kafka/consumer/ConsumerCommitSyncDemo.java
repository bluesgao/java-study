package com.bluesgao.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ConsumerCommitSyncDemo {
    public static final AtomicBoolean isRunning = new AtomicBoolean(true);

    public static Properties initConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "PLAINTEXT://47.97.205.190:9093");
        props.put("group.id", "g1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    public static void main(String[] args) {

    }

    private static void OneCommit() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(initConfig());
        String topic = "test";
        List<String> topicList = Arrays.asList(topic);
        consumer.subscribe(topicList);
        //带参数的同步位移提交
        try {
            while (isRunning.get()) {
                ConsumerRecords<String, String> records = consumer.poll(1000);
                for (ConsumerRecord<String, String> record : records) {
                    //do some logical processing.
                    long offset = record.offset();
                    TopicPartition partition = new TopicPartition(record.topic(), record.partition());
                    //每消费一条消息就提交一次消费位移
                    consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(offset + 1)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }

    private static void partitionCommit() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(initConfig());
        consumer.subscribe(Arrays.asList("test"));
        try {
            while (isRunning.get()) {
                System.out.println("poll start...");
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
                for (TopicPartition partition : records.partitions()) {
                    List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
                    for (ConsumerRecord<String, String> record : partitionRecords) {
                        //do some logical processing.
                        System.out.println(record.partition() + " : " + record.value());
                    }
                    //按照分区的粒度提交位移
                    long lastConsumedOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                    consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastConsumedOffset + 1)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }

    }
}
