package com.bluesgao.swaggerdemo.common;

import java.io.Serializable;

public class Result<V> implements Serializable {
    private int code;
    private String info;
    private V value;

    private Result() {
    }

    private Result(Builder<V> builder) {
        this.code = builder.code;
        this.info = builder.info;
        this.value = builder.value;
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public V getValue() {
        return value;
    }

    public static Result.Builder builder(){
        return new Result.Builder();
    }
    /**
     * 建造者
     *
     * @param <V>
     */
    public static class Builder<V> {
        private int code;
        private String info;
        private V value;

        public Builder<V> code(int code) {
            this.code = code;
            return this;
        }

        public Builder<V> info(String info) {
            this.info = info;
            return this;
        }

        public Builder<V> value(V value) {
            this.value = value;
            return this;
        }

        public Result build() {
            return new Result(this);
        }
    }
}
