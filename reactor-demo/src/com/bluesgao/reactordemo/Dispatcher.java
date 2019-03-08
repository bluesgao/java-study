package com.bluesgao.reactordemo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Dispatcher {
    private Map<EventType, EventHandler> eventHandlerMap = new ConcurrentHashMap<>();
    private Selector selector;

    public Dispatcher(Selector selector) {
        this.selector = selector;
    }

    public void registEventHandler(EventType eventType, EventHandler eventHandler) {
        eventHandlerMap.put(eventType, eventHandler);
    }

    public void removeEventHandler(EventType eventType) {
        eventHandlerMap.remove(eventType);
    }

    public void handleEvents() {
        System.out.println("Dispatcher handleEvents");
        dispatch();
    }

    private void dispatch() {
        while (true) {
            List<Event> events = selector.select(1);
            System.out.println(Thread.currentThread().getName()+" Dispatcher dispatch events.size:" + events.size());
            for (Event event : events) {
                System.out.println(Thread.currentThread().getName()+" Dispatcher dispatch event:" + event.toString());
                EventHandler eventHandler = eventHandlerMap.get(event.getEventType());
                eventHandler.handle(event);
            }
        }
    }
}
