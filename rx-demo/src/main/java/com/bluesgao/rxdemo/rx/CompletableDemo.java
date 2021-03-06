package com.bluesgao.rxdemo.rx;

import io.reactivex.Completable;

public class CompletableDemo {
    public static void main(String[] args) {
        Completable.create(emitter -> {
            emitter.onComplete();
        }).subscribe(System.out::println);
    }
}
