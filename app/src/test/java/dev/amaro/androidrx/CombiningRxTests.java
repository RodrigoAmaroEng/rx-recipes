package dev.amaro.androidrx;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;

import static dev.amaro.androidrx.CreationRxTests.printEmitted;

public class CombiningRxTests {

    @Test
    public void observableCombineMethod() throws Exception {
        Observable<Integer> observable1 = Observable.just(1, 2, 3);
        Observable<Integer> observable2 = Observable.just(4, 5, 6);
        // http://reactivex.io/RxJava/javadoc/io/reactivex/Observable.html#mergeWith-io.reactivex.CompletableSource-
        observable1.mergeWith(observable2).subscribe(printEmitted());
    }

    @Test
    public void observableZipMethod() throws Exception {
        Observable<Integer> observable1 = Observable.just(1, 2, 3);
        Observable<Integer> observable2 = Observable.just(4, 5, 6);
        // http://reactivex.io/RxJava/javadoc/io/reactivex/Observable.html#zipWith-java.lang.Iterable-io.reactivex.functions.BiFunction-
        observable1.zipWith(observable2, (integer, integer2) -> integer + integer2).subscribe(printEmitted());
    }
}
