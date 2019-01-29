package com.bluesgao.rxdemo;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class SingleDemo {
    public static void main(String[] args) {
        Single.<String>create(emitter -> {
            emitter.onSuccess("hello 1");
            emitter.onSuccess("hello 2");
        }).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscibe "+ d);
            }

            @Override
            public void onSuccess(String s) {
                System.out.println("onSuccess " + s);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }
        });
    }
}
