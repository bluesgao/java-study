package com.bluesgao.storm.wordcount;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;

/**
 * ClassName: WordCountTopology
 * Desc: TODO
 * Author: gaoxin11
 * Date: 2018/7/18 11:30
 **/
public class WordCountTopology {
    private static final String SENTENCE_SPOUT_ID = "sentence-spout";
    private static final String SPLIT_BOLT_ID = "split-bolt";
    private static final String COUNT_BOLT_ID = "count-bolt";
    private static final String REPORT_BOLT_ID = "report-bolt";
    private static final String TOPOLOGY_NAME = "word-count-topology";

    public static void main(String[] args) {
        System.out.println("---word count topology main start---");
        SentenceSpout spout = new SentenceSpout();
        SplitSentenceBolt splitSentenceBolt = new SplitSentenceBolt();
        WordCountBolt wordCountBolt = new WordCountBolt();
        ReportBolt reportBolt = new ReportBolt();

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout(SENTENCE_SPOUT_ID, spout);
        builder.setBolt(SPLIT_BOLT_ID, splitSentenceBolt).shuffleGrouping(SENTENCE_SPOUT_ID);
        builder.setBolt(COUNT_BOLT_ID, wordCountBolt).shuffleGrouping(SPLIT_BOLT_ID);
        builder.setBolt(REPORT_BOLT_ID, reportBolt).shuffleGrouping(COUNT_BOLT_ID);

        Config config = new Config();

        try {
            //运行拓扑
            if (args != null && args.length > 0) { //有参数时，表示向集群提交作业，并把第一个参数当做topology名称
                System.out.println("远程模式");
                StormSubmitter.submitTopology(args[0], config, builder.createTopology());
            } else {//没有参数时，本地提交
                //启动本地模式
                System.out.println("本地模式");
                LocalCluster cluster = new LocalCluster();
                cluster.submitTopology(TOPOLOGY_NAME, config, builder.createTopology());
                Thread.sleep(10 * 1000);
                //  关闭本地集群
                cluster.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("---word count topology main end---");
    }
}
