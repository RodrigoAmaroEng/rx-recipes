package br.com.pagseguro.androidrx;

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
        // Zip só funciona pois apenas um objeto é retornado por cada chamada
        TestObserver<Object> test = Observable.zip(call1, call2, new BiFunction<AccountVO, BankVO, Object>() {
            @Override
            public Object apply(@NonNull AccountVO accountVO, @NonNull BankVO bankVO) throws Exception {
                return new Object(); // Montado a partir das informações de ambos
            }
        }).subscribeOn(Schedulers.newThread())
                .test();
        test.awaitTerminalEvent();
        test.assertValueCount(1);
    }

    @Test
    public void combinedSequentialApiCall() throws Exception {
        RestApi instance = RestAdapter.instance();
        Observable<AccountVO> call1 = instance.getAccountInfo("1");
        Observable<BankVO> call2 = instance.getBankInfo("1");
        // Zip só funciona pois apenas um objeto é retornado por cada chamada
        TestObserver<Object> test = instance.getAccountInfo("1")
                .zipWith(call2, new BiFunction<AccountVO, BankVO, Object>() {
                    @Override
                    public Object apply(@NonNull AccountVO accountVO, @NonNull BankVO bankVO) throws Exception {
                        return new Object(); // Montado a partir das informações de ambos
                    }
                }).subscribeOn(Schedulers.newThread())
                .test();
        test.awaitTerminalEvent();
        test.assertValueCount(1);
    }

}
