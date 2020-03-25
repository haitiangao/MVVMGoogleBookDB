package com.example.mvvmgooglebookdb.database;


import android.app.Application;

import com.example.mvvmgooglebookdb.model.Book;
import com.example.mvvmgooglebookdb.model.FavouriteBook;
import com.example.mvvmgooglebookdb.util.DebugLogger;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.mvvmgooglebookdb.util.DebugLogger.logDebug;
import static com.example.mvvmgooglebookdb.util.DebugLogger.logError;

public class BookRepository {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private BookDao bookDao;
    private List<FavouriteBook> favouriteBooks;

    public BookRepository(Application application){
        BookRoomDatabase db =BookRoomDatabase.getDatabase(application);
        bookDao =db.bookDao();
        favouriteBooks=bookDao.getFavouriteBooks();
    }

    public List<FavouriteBook> getAllFavourites() {
        return favouriteBooks;
    }

    public void insertAFavourite(FavouriteBook favouriteBook){
        Disposable disposable  =Completable.fromAction(()-> bookDao.insertAFavourite(favouriteBook))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->logDebug("Insert Successful"),
                        throwable -> logError(throwable));
        compositeDisposable.add(disposable);
        //compositeDisposable.dispose();
    }

    public void deleteAFavourite(FavouriteBook favouriteBook){
        Disposable disposable  =Completable.fromAction(()-> bookDao.deleteAFavourite(favouriteBook.getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->logDebug("Delete Successful"),
                        throwable -> logError(throwable));
        compositeDisposable.add(disposable);
        //DebugLogger.logDebug("repo fav: "+bookDao.getFavouriteBooks().size());

        //compositeDisposable.dispose();

    }

    public void disposeDisposables(){
        compositeDisposable.dispose();
    }



}
