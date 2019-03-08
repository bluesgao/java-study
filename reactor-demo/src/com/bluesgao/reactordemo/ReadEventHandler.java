package com.bluesgao.reactordemo;

public class ReadEventHandler extends EventHandler {
    private Selector selector;

    public ReadEventHandler(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void handle(Event event) {
        if (event.getEventType() == EventType.READ) {
            System.out.println(Thread.currentThread().getName()+" ReadEventHandler handle:" + event.toString());
        }
    }
}
