package com.bluesgao.reactordemo;

public class Event {
    private InputSource inputSource;
    private EventType eventType;

    public InputSource getInputSource() {
        return inputSource;
    }

    public Event setInputSource(InputSource inputSource) {
        this.inputSource = inputSource;
        return this;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Event setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    @Override
    public String toString() {
        return "Event{" +
                "inputSource=" + inputSource +
                ", eventType=" + eventType +
                '}';
    }
}
