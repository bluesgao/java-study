package com.bluesgao.demo.agent;

import com.bluesgao.demo.agent.asm.LogTransformer;

import java.lang.instrument.Instrumentation;

public class PreMain {
    public static void premain(String options, Instrumentation instrumentation){
        instrumentation.addTransformer(new LogTransformer());
    }
}
