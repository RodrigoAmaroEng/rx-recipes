package br.com.pagseguro.androidrx;


import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

import static br.com.pagseguro.androidrx.CreationRxTests.printEmitted;

public class TransformingRxTests {

    @Test
    public void observableMapFunction() throws Exception {
        Observable<Integer> range = Observable.range(1, 5);
        range.map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return "String: " + integer;
            }
        }).subscribe(printEmitted());
    }

    @Test
    public void observableFlatMapFunction() throws Exception {
        Observable<List<Integer>> range = Observable.just(Arrays.asList(1, 2, 3, 4, 5));
        range.flatMap(new Function<List<Integer>, ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> apply(@NonNull List<Integer> integers) throws Exception {
                System.out.println("Event: " + integers.size());
                return Observable.fromIterable(integers);
            }
        }).subscribe(printEmitted());
    }
}
