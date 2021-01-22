package com.bluesgao.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ConsumerPartitionDemo {
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
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(initConfig());
        consumer.subscribe(Arrays.asList("test"));

        try {
            while (true) {
                System.out.println("poll start...");
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
                for (TopicPartition partition : records.partitions()) {
                    for (ConsumerRecord<String, String> record : records.records(partition)) {
                        System.out.println(record.partition() + " : " + record.value());
                    }
                }
            }
        } catch (Exception e) {
            log.error("occur exception ", e);
        } finally {
            consumer.close();
        }
    }
}
