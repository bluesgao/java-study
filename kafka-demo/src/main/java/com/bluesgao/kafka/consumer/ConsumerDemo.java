package com.bluesgao.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ConsumerDemo {
    public static final AtomicBoolean isRunning = new AtomicBoolean(true);

    public static Properties initConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "PLAINTEXT://47.97.205.190:9092");
        props.put("group.id", "g2");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //从何处开始消费,latest 表示消费最新消息,earliest 表示从头开始消费,none表示抛出异常,默认latest
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        return props;
    }

    public static void main(String[] args) {

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(initConfig());

        List<String> topicList = Arrays.asList("mysql.user", "db-user.user.t_user");
        consumer.subscribe(topicList);
        try {
            while (isRunning.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (String topic : topicList) {
                    for (ConsumerRecord<String, String> record : records.records(topic)) {
                        System.out.println("读取到的数据:"+record.topic() + " : " + record.value());
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
