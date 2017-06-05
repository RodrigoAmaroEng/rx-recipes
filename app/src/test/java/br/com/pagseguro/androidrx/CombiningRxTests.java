package br.com.pagseguro.androidrx;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;

import static br.com.pagseguro.androidrx.CreationRxTests.printEmitted;

public class CombiningRxTests {

    @Test
    public void observableCombineMethod() throws Exception {
        Observable<Integer> observable1 = Observable.just(1, 2, 3);
        Observable<Integer> observable2 = Observable.just(4, 5, 6);
        observable1.mergeWith(observable2).subscribe(printEmitted());
    }

    @Test
    public void observableZipMethod() throws Exception {
        Observable<Integer> observable1 = Observable.just(1, 2, 3);
        Observable<Integer> observable2 = Observable.just(4, 5, 6);
        observable1.zipWith(observable2, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).subscribe(printEmitted());
    }
}
