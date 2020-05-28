package dev.amaro.androidrx;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class RecipesTests {
    @Test
    public void onClickRecipeTest() throws Exception {
        PublishSubject<AccountVO> publisher = PublishSubject.create();
        TestObserver<AccountVO> test = publisher.test();
        publisher.onNext(new AccountVO());
        publisher.onComplete();
        test.awaitTerminalEvent();
        test.assertValueCount(1);
    }

    @Test
    public void simpleApiCall() throws Exception {
        TestObserver<BankVO> test = RestAdapter.instance().getBankInfo("1")
                .subscribeOn(Schedulers.newThread())
                .test();
        test.awaitTerminalEvent();
        test.assertValueCount(1);
    }

    @Test
    public void combinedNotSequentialApiCall() throws Exception {
        RestApi instance = RestAdapter.instance();
        Observable<AccountVO> call1 = instance.getAccountInfo("1");
        Observable<BankVO> call2 = instance.getBankInfo("1");
        // http://reactivex.io/RxJava/javadoc/io/reactivex/Observable.html#zip-java.lang.Iterable-io.reactivex.functions.Function-
        TestObserver<Object> test = Observable.zip(call1, call2, (accountVO, bankVO) -> new Object())
                .subscribeOn(Schedulers.newThread())
                .test();
        test.awaitTerminalEvent();
        test.assertValueCount(1);
    }

    @Test
    public void combinedSequentialApiCall() throws Exception {
        RestApi instance = RestAdapter.instance();
        Observable<AccountVO> call1 = instance.getAccountInfo("1");
        Observable<BankVO> call2 = instance.getBankInfo("1");
        // http://reactivex.io/RxJava/javadoc/io/reactivex/Observable.html#zipWith-java.lang.Iterable-io.reactivex.functions.BiFunction-
        TestObserver<Object> test = instance.getAccountInfo("1")
                .zipWith(call2, (accountVO, bankVO) -> new Object())
                .subscribeOn(Schedulers.newThread())
                .test();
        test.awaitTerminalEvent();
        test.assertValueCount(1);
    }

}
