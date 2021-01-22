package com.bluesgao.rxdemo.rx;

import io.reactivex.Maybe;

public class MaybeDemo {
    public static void main(String[] args) {
        Maybe.<String>create(emitter -> {
            emitter.onSuccess("hello 1");
            emitter.onComplete();
            emitter.onSuccess("hello 2");
        }).subscribe(System.out::println);
    }
}
