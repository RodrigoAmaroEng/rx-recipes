package br.com.pagseguro.androidrx;


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
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                System.out.println("SubscribeThreadId: " + Thread.currentThread().getId());
                e.onNext(1);
                e.onNext(2);
                e.onComplete();
            }
        });
        observable.subscribeOn(Schedulers.newThread()).subscribe(printTThreadId());
    }

    @Test
    public void observableObserveOnMethod() throws Exception {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                System.out.println("SubscribeThreadId: " + Thread.currentThread().getId());
                e.onNext(1);
                e.onNext(2);
                e.onComplete();
            }
        });
        observable.observeOn(Schedulers.newThread()).subscribe(printTThreadId());
    }

    @android.support.annotation.NonNull
    private Consumer<Integer> printTThreadId() {
        return new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                System.out.println("ObserveThreadId: " + Thread.currentThread().getId());
            }
        };
    }
}
