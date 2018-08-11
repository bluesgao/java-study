package com.bluesgao.storm.trident;

import com.bluesgao.storm.trident.aggregate.CountAggreator;
import com.bluesgao.storm.trident.function.OrderDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.kafka.trident.OpaqueTridentKafkaSpout;
import org.apache.storm.kafka.trident.TridentKafkaConfig;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

@Slf4j
public class OrderAnalysisTopology {
    public static void main(String[] args) {

        BrokerHosts kafkaHosts = new ZkHosts("localhost:2181");
        TridentKafkaConfig kafkaConfig = new TridentKafkaConfig(kafkaHosts,"test");
        OpaqueTridentKafkaSpout kafkaSpout = new OpaqueTridentKafkaSpout(kafkaConfig);

        TridentTopology tridentTopology = new TridentTopology();
        Stream stream = tridentTopology.newStream("kafka-spout", kafkaSpout)
                .each(new Fields("order"), new OrderDeserializer(), new Fields("orderNo", "payTime", "amount"));

        TridentState countByOrder = stream.project(new Fields("amount"))
                .groupBy(new Fields("amount"))
                .persistentAggregate(new MemoryMapState.Factory(), new CountAggreator(), new Fields("order-count"));

        try {

            //启动本地模式
            log.info("本地模式");
            Config config = new Config();
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("kafkaTridentTest", config, tridentTopology.build());
            Thread.sleep(1 * 1000);
            //  关闭本地集群
            //cluster.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
