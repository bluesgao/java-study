package com.bluesgao.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.ProducerFencedException;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ProducerTest {
    static void sendMsg() throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "PLAINTEXT://47.97.205.190:9092");
        props.put("acks", "1");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> procuder = new KafkaProducer<String, String>(props);

        String topic = "gx-test-topic";
        for (int i = 1; i <= 10; i++) {
            String value = " this is another message_" + i;
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, i + "", value);
            procuder.send(record, new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    System.out.println("message send to partition " + metadata.partition() + ", offset: " + metadata.offset());
                }
            });
        }
        System.out.println("send message over.");
        procuder.close();
    }

    public static void main(String[] args) throws Exception {
        try {
            sendMsg();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
