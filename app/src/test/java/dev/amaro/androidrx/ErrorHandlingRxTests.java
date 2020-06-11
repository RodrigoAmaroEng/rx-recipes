package dev.amaro.androidrx;


import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DefaultObserver;

import static dev.amaro.androidrx.CreationRxTests.printEmitted;

public class ErrorHandlingRxTests {
    @Test
    public void observableCatchMethod() throws Exception {
        Observable<Integer> observable = Observable.create(e -> {
            e.onNext(1);
            e.onNext(2);
            e.onError(new IllegalStateException());
            e.onNext(4);
        });
        observable.subscribe(new DefaultObserver<Integer>() {
            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println(integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Erro");
            }

            @Override
            public void onComplete() {
                System.out.println("Complete");
            }
        });
    }

    @Test
    public void observableRetryMethod() throws Exception {
        final AtomicReference<Boolean> throwError = new AtomicReference<>(true);
        Observable<Integer> observable = Observable.create(e -> {
            e.onNext(1);
            e.onNext(2);
            if (throwError.get()) {
                throwError.set(false);
                e.onError(new IllegalStateException());
            }
            e.onNext(4);
        });
        // http://reactivex.io/RxJava/javadoc/io/reactivex/Observable.html#retry--
        observable.retry().subscribe(printEmitted());
    }


}
