package com.bluesgao.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class ConsumerTest {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "PLAINTEXT://47.97.205.190:9092");
        props.put("group.id", "1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String ,String>(props);
        consumer.subscribe(Arrays.asList("gx-test-topic"));
        while (true) {
            System.out.println("poll start...");
            ConsumerRecords<String, String> records = consumer.poll(5);
            int count = records.count();
            System.out.println("the numbers of topic:" + count);
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("\n offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
            try {
                Thread.sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
