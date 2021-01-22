package com.bluesgao.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ConsumerOffsetDemo {
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

        //消费位移的演示
        String topic = "test";
        List<String> topicList = Arrays.asList(topic);
        TopicPartition tp = new TopicPartition(topic, 0);
        consumer.assign(Arrays.asList(tp));
        consumer.subscribe(topicList);
        //当前消费到的位移
        long lastConsumedOffset = -1;
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
            if (records.isEmpty()) {
                break;
            }
            List<ConsumerRecord<String, String>> partitionRecords = records.records(tp);
            lastConsumedOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
            //同步提交消费位移
            consumer.commitSync();
        }
        System.out.println("comsumed offset is " + lastConsumedOffset);
        OffsetAndMetadata offsetAndMetadata = consumer.committed(tp);
        System.out.println("commited offset is " + offsetAndMetadata.offset());
        long position = consumer.position(tp);
        System.out.println("the offset of the next record is " + position);
    }
}
