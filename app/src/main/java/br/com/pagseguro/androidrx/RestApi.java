package br.com.pagseguro.androidrx;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {

    @GET("/account")
    Observable<AccountVO> getAccountInfo(@Query("token") String token);

    @GET("/bank")
    Observable<BankVO> getBankInfo(@Query("token") String token);

}
