package dev.amaro.androidrx;


import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

import static dev.amaro.androidrx.CreationRxTests.printEmitted;

public class TransformingRxTests {

    @Test
    public void observableMapFunction() throws Exception {
        Observable<Integer> range = Observable.range(1, 5);
        range.map(integer -> "String: " + integer).subscribe(printEmitted());
    }

    @Test
    public void observableFlatMapFunction() throws Exception {
        Observable<List<Integer>> range = Observable.just(Arrays.asList(1, 2, 3, 4, 5));
        // http://reactivex.io/RxJava/javadoc/io/reactivex/Observable.html#flatMap-io.reactivex.functions.Function-
        range
                .doOnNext(it -> System.out.println(it))
                .flatMap((Function<List<Integer>, ObservableSource<Integer>>) integers -> {
                    System.out.println("Event: " + integers.size());
                    return Observable.fromIterable(integers);
                }).subscribe(printEmitted());
    }
}
