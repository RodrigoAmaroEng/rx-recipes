package br.com.pagseguro.androidrx;

import org.junit.Test;

import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class CreationRxTests {
    @Test
    public void observableCreateMethod() throws Exception {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(4);
                e.onComplete();
            }
        });
        observable.subscribe(printEmitted());
    }

    @Test
    public void observableJustMethod() throws Exception {
        Observable<String> observable = Observable.just("teste", "asd", "asdfdfg");
        observable.subscribe(printEmitted());
    }

    @Test
    public void observableFromMethod() throws Exception {
        Observable<String> observable = Observable.fromIterable(Arrays.asList("T1", "T2"));
        observable.subscribe(printEmitted());
    }

    @Test
    public void observableRangeMethod() throws Exception {
        Observable<Integer> observable = Observable.range(10, 20);
        observable.subscribe(printEmitted());
    }

    @android.support.annotation.NonNull
    public static <T> Consumer<T> printEmitted() {
        return new Consumer<T>() {
            @Override
            public void accept(@NonNull T integer) throws Exception {
                System.out.println(integer);
            }
        };
    }
}