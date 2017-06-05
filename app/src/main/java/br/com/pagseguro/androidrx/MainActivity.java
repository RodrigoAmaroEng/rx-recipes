package br.com.pagseguro.androidrx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {
    EditText field;
    PublishSubject<String> publisher = PublishSubject.create();
    TextView results;
    StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        results = (TextView) findViewById(R.id.results);
        field = (EditText) findViewById(R.id.searchField);
        field.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                publisher.onNext(field.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        publisher
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        return s != null && s.length() > 1;
                    }
                })
                .throttleLast(100, TimeUnit.MILLISECONDS)
                .debounce(300, TimeUnit.MILLISECONDS)
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull String s) throws Exception {
                        sb = new StringBuilder();
                        return search(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        sb.append(s).append(", ");
                        results.setText(sb);
                    }
                });
    }

    private ObservableSource<String> search(final String query) {
        List<String> countries = Arrays.asList("Argentina", "Bolívia", "Brasil", "Chile", "Colômbia", "Equador", "Guiana",
                "Guiana Francesa", "Paraguai", "Peru", "Suriname", "Venezuela", "Uruguai");
        return Observable.fromIterable(countries)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        return s.toLowerCase().startsWith(query);
                    }
                });
    }
}
