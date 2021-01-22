package com.bluesgao.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;

@Slf4j
public class ProducerDemo {
    static void sendMsg() throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "PLAINTEXT://47.97.205.190:9092");
        //props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"PLAINTEXT://47.97.205.190:9092,PLAINTEXT://47.97.205.190:19092,PLAINTEXT://47.97.205.190:29092");
        props.put("acks", "1");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> procuder = new KafkaProducer<String, String>(props);

        String topic = "test";
        for (int i = 1; i <= 10_000; i++) {
            String value = " this is another message_" + i;
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, i + "", value);
            procuder.send(record, new Callback() {
                @Override
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
