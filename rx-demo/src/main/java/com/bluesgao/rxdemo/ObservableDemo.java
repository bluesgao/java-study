package com.bluesgao.rxdemo;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ObservableDemo {
    public static void main(String[] args) {

        Observable<String> observable = Observable.create(emitter -> {
            emitter.onNext("hello 1");
            emitter.onNext("hello 2");
            emitter.onComplete();
            emitter.onNext("hello 3");
        });

        Observer<String> observer = new Observer<String>() {
            public void onSubscribe(Disposable disposable) {
                System.out.println("observer onSubscribe" + Thread.currentThread().getId());
            }

            public void onNext(String s) {
                System.out.println("observer onNext " + s +" "+ Thread.currentThread().getId());
            }

            public void onError(Throwable throwable) {
                System.out.println("observer onError");
            }

            public void onComplete() {
                System.out.println("observer onComplete" + Thread.currentThread().getId());
            }
        };

        observable.subscribe(observer);
    }
}
