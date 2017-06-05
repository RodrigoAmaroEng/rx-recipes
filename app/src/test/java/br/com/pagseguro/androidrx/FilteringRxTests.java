package br.com.pagseguro.androidrx;


import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;

import static br.com.pagseguro.androidrx.CreationRxTests.printEmitted;

public class FilteringRxTests {
    @Test
    public void observableDistinctFunction() throws Exception {
        Observable<Integer> observable = Observable.just(1, 1, 2, 3, 4, 5, 3, 4, 6);
        observable.distinct().subscribe(printEmitted());
    }

    @Test
    public void observableFilterFunction() throws Exception {
        Observable<Integer> observable = Observable.just(1, 1, 2, 3, 4, 5, 3, 4, 6);
        observable.filter(new AppendOnlyLinkedArrayList.NonThrowingPredicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer > 3;
            }
        }).distinct().subscribe(printEmitted());
    }
}
