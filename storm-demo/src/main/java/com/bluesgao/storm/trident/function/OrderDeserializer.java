package com.bluesgao.storm.trident.function;

import com.bluesgao.storm.trident.domain.Order;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.operation.TridentOperationContext;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

@Slf4j
public class OrderDeserializer extends BaseFunction {
    private Gson gson;

    public void execute(TridentTuple tuple, TridentCollector collector) {
        String orderStr = tuple.getString(0);
        log.info("orderStr:", orderStr);

        Order order = gson.fromJson(orderStr, Order.class);

        collector.emit(new Values(order.getAmount(), order.getPayTime()));

    }

    @Override
    public void prepare(Map conf, TridentOperationContext context) {
        super.prepare(conf, context);
        gson = new Gson();
    }
}
