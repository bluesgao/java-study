package com.bluesgao.rxdemo.rx;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class FlowableDemo {
    public static void main(String[] args) {
        Flowable.<String>create(emitter -> {
            emitter.onNext("hello 1");
            emitter.onNext("hello 2");
            emitter.onComplete();
            emitter.onNext("hello 3");
        }, BackpressureStrategy.MISSING).subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext" + s);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onErrror");
            }

            @Override
            public void onComplete(){
                System.out.println("onComplete");
            }
        });
    }
}
