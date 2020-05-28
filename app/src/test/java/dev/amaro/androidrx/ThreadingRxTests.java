package dev.amaro.androidrx;


import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ThreadingRxTests {
    @Test
    public void observableSubscribeOnMethod() throws Exception {
        Observable<Integer> observable = Observable.create(e -> {
            System.out.println("SubscribeThreadId: " + Thread.currentThread().getId());
            e.onNext(1);
            e.onNext(2);
            e.onComplete();
        });
        observable.subscribeOn(Schedulers.newThread()).subscribe(printTThreadId());
    }

    @Test
    public void observableObserveOnMethod() throws Exception {
        Observable<Integer> observable = Observable.create(e -> {
            System.out.println("SubscribeThreadId: " + Thread.currentThread().getId());
            e.onNext(1);
            e.onNext(2);
            e.onComplete();
        });
        observable.observeOn(Schedulers.newThread()).subscribe(printTThreadId());
    }

    private Consumer<Integer> printTThreadId() {
        return integer -> System.out.println("ObserveThreadId: " + Thread.currentThread().getId());
    }
}
