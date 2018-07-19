package com.bluesgao.storm.wordcount;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * ClassName: SentenceSpout
 * Desc: TODO
 * Author: gaoxin11
 * Date: 2018/7/18 11:07
 **/
public class SentenceSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;
    private int index = 0;
    private String[] sentences = {"hello world", "hello storm", "hello java", "hello jvm", "hello spring"};

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
    }

    public void nextTuple() {
        System.out.println("SentenceSpout nextTuple:发射数据："+ sentences[index]);
        this.collector.emit(new Values(sentences[index]));
        index++;
        if (index >= sentences.length) {
            index = 0;
        }
        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException e) {
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        System.out.println("SentenceSpout 定义格式...");
        outputFieldsDeclarer.declare(new Fields("sentence"));
    }
}
