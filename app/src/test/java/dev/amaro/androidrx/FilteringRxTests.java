package dev.amaro.androidrx;


import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;

import static dev.amaro.androidrx.CreationRxTests.printEmitted;

public class FilteringRxTests {
    @Test
    public void observableDistinctFunction() throws Exception {
        Observable<Integer> observable = Observable.just(1, 1, 2, 3, 4, 5, 3, 4, 6);
        // http://reactivex.io/RxJava/javadoc/io/reactivex/Observable.html#distinct--
        observable.distinct().subscribe(printEmitted());
    }

    @Test
    public void observableFilterFunction() throws Exception {
        Observable<Integer> observable = Observable.just(1, 1, 2, 3, 4, 5, 3, 4, 6);
        // http://reactivex.io/RxJava/javadoc/io/reactivex/Observable.html#filter-io.reactivex.functions.Predicate-
        observable.filter((AppendOnlyLinkedArrayList.NonThrowingPredicate<Integer>) integer -> integer > 3).distinct().subscribe(printEmitted());
    }
}
