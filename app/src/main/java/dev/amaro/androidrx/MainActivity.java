package dev.amaro.androidrx;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
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
                .throttleLast(100, TimeUnit.MILLISECONDS) // http://reactivex.io/RxJava/javadoc/io/reactivex/Observable.html#throttleLast-long-java.util.concurrent.TimeUnit-
                .debounce(300, TimeUnit.MILLISECONDS) // http://reactivex.io/RxJava/javadoc/io/reactivex/Observable.html#debounce-io.reactivex.functions.Function-
                .filter(s -> s != null && s.length() > 1)
                .flatMap((Function<String, ObservableSource<String>>) s -> {
                    sb = new StringBuilder();
                    return search(s.toLowerCase());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    sb.append(s).append(", ");
                    results.setText(sb);
                });
    }

    private ObservableSource<String> search(final String query) {
        List<String> countries = Arrays.asList("Argentina", "Bolívia", "Brasil", "Chile", "Colômbia", "Equador", "Guatemala", "Guiana",
                "Guiana Francesa", "México", "Paraguai", "Peru", "Suriname", "Venezuela", "Uruguai");
        return Observable.fromIterable(countries)
                .filter(s -> s.toLowerCase().startsWith(query));
    }
}
