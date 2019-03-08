package com.bluesgao.reactordemo;

public class InputSource {
    private Object data;
    private Long id;

    public InputSource(Object data, Long id) {
        this.data = data;
        this.id = id;
    }

    public InputSource() {
    }

    public Object getData() {
        return data;
    }

    public InputSource setData(Object data) {
        this.data = data;
        return this;
    }

    public Long getId() {
        return id;
    }

    public InputSource setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "InputSource{" +
                "data=" + data +
                ", id=" + id +
                '}';
    }
}
