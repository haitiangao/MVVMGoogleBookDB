package com.example.mvvmgooglebookdb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.mvvmgooglebookdb.database.BookRepository;
import com.example.mvvmgooglebookdb.model.Book;
import com.example.mvvmgooglebookdb.model.FavouriteBook;
import com.example.mvvmgooglebookdb.model.Library;
import com.example.mvvmgooglebookdb.network.BookRetrofitService;
import com.example.mvvmgooglebookdb.util.DebugLogger;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BookViewModel extends AndroidViewModel {
    private BookRetrofitService bookRetrofitService;
    private BookRepository bookRepository;
    private List<FavouriteBook> favouriteBooks;

    public BookViewModel(@NonNull Application application) {
        super(application);
        bookRepository = new BookRepository(application);
        bookRetrofitService =new BookRetrofitService();
        favouriteBooks = bookRepository.getAllFavourites();
    }

    public Observable<Library> getBookListRx(String query) {
        return  bookRetrofitService
                .getBooksRx(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Observable<Book> getSpecificBookRx(String ID){
        return  bookRetrofitService
                .getSpecificBookRx(ID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public List<FavouriteBook> getAllFavourites(){
        return favouriteBooks;

    }

    public void insertAFavourite(Book book){
        FavouriteBook favouriteBook = new FavouriteBook(book.getId());
        bookRepository.insertAFavourite(favouriteBook);
    }

    public void deleteAFavourite(Book book)
    {
        FavouriteBook favouriteBook = new FavouriteBook(book.getId());
        bookRepository.deleteAFavourite(favouriteBook);
        //DebugLogger.logDebug("Model fav: "+bookRepository.getAllFavourites().size());
    }

    public void disposeDisposables(){
        bookRepository.disposeDisposables();
    }

}
