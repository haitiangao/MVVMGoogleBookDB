package com.example.mvvmgooglebookdb.network;

import com.example.mvvmgooglebookdb.model.Book;
import com.example.mvvmgooglebookdb.model.Library;
import com.example.mvvmgooglebookdb.util.Constants;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.mvvmgooglebookdb.util.Constants.API_KEY;

public class BookRetrofitService {
    private BookService bookService;
    private OkHttpClient client;

    public BookRetrofitService(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        bookService = createGoogleService(getRetrofitInstance());
    }


    private Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private BookService createGoogleService(Retrofit retrofitInstance) {
        return retrofitInstance.create(BookService.class);
    }

    public Observable<Library> getBooksRx(String query) {
        return bookService.getBooksRx(query, API_KEY);
    }

    public Observable<Book> getSpecificBookRx(String ID){
        return bookService.getSpecificBookRx(ID);
    }
}
