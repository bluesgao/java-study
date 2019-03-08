package com.bluesgao.reactordemo;

public class AcceptEventHandler extends EventHandler {
    private Selector selector;

    public AcceptEventHandler(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void handle(Event event) {
        //处理Accept的event事件
        if (event.getEventType() == EventType.ACCEPT) {
            //TODO 处理ACCEPT状态的事件
            System.out.println(Thread.currentThread().getName()+" AcceptEventHandler handle:" + event.toString());

            //将事件状态改为下一个READ状态，并放入selector的缓冲队列中
            Event readEvent = new Event();
            readEvent.setInputSource(event.getInputSource());
            readEvent.setEventType(EventType.READ);
            selector.addEvent(readEvent);
        }
    }
}
