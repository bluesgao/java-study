package com.bluesgao.storm.trident.aggregate;

import lombok.Data;
import org.apache.storm.trident.operation.CombinerAggregator;
import org.apache.storm.trident.tuple.TridentTuple;

@Data
public class CountAggreator implements CombinerAggregator<Long> {
    public Long init(TridentTuple tuple) {
        return 1L;
    }

    public Long combine(Long val1, Long val2) {
        return val1+val2;
    }

    public Long zero() {
        return 0L;
    }
}
