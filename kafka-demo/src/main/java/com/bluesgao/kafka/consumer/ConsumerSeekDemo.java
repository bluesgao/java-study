package com.bluesgao.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ConsumerSeekDemo {
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

    private static void seek() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(initConfig());
        String topic = "test";
        List<String> topicList = Arrays.asList(topic);
        consumer.subscribe(topicList);
        consumer.poll(Duration.ofMillis(10000));

        //获取分区的分配信息
        Set<TopicPartition> assignment = consumer.assignment();
        for (TopicPartition tp : assignment) {
            //重置消费者分配到的分区的消费位置
            consumer.seek(tp, 10);
        }
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            //consume the record.
        }
    }
}
