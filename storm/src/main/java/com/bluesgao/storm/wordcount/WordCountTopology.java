package com.bluesgao.storm.wordcount;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
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

    public static void main(String[] args) throws Exception {
        SentenceSpout spout = new SentenceSpout();
        SplitSentenceBolt splitSentenceBolt = new SplitSentenceBolt();
        WordCountBolt wordCountBolt = new WordCountBolt();
        ReportBolt reportBolt = new ReportBolt();

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout(SENTENCE_SPOUT_ID, spout);
        builder.setBolt(SPLIT_BOLT_ID, splitSentenceBolt);
        builder.setBolt(COUNT_BOLT_ID, wordCountBolt);
        builder.setBolt(REPORT_BOLT_ID, reportBolt);

        Config config = new Config();
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(TOPOLOGY_NAME, config, builder.createTopology());

        Thread.sleep(10 * 1000);

        cluster.killTopology(TOPOLOGY_NAME);
        cluster.shutdown();
    }
}
