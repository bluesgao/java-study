package com.bluesgao.reactordemo;

public abstract class EventHandler {
    private InputSource source;
    public abstract void handle(Event event);

    public InputSource getSource() {
        return source;
    }

    public EventHandler setSource(InputSource source) {
        this.source = source;
        return this;
    }
}
