package com.example.mvvmgooglebookdb.network;

import com.example.mvvmgooglebookdb.model.Book;
import com.example.mvvmgooglebookdb.model.Library;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.example.mvvmgooglebookdb.util.Constants.GET_URL_POSTFIX;

public interface BookService {

    //"https://www.googleapis.com/books/v1/volumes?q={search term}&key={YOUR_API_KEY}”

    @GET(GET_URL_POSTFIX)
    Observable<Library> getBooksRx(@Query("q")String query, @Query("key")String apiKey);

    @GET(GET_URL_POSTFIX+"/{ID}")
    Observable<Book> getSpecificBookRx(@Path("ID") String ID);

}
