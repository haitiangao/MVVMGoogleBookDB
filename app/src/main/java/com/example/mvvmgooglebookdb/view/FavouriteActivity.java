package com.example.mvvmgooglebookdb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.EditText;

import com.example.mvvmgooglebookdb.R;
import com.example.mvvmgooglebookdb.adapter.BookAdapter;
import com.example.mvvmgooglebookdb.adapter.FavouriteAdapter;
import com.example.mvvmgooglebookdb.model.Book;
import com.example.mvvmgooglebookdb.model.FavouriteBook;
import com.example.mvvmgooglebookdb.util.DebugLogger;
import com.example.mvvmgooglebookdb.viewmodel.BookViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;

public class FavouriteActivity extends AppCompatActivity implements FavouriteAdapter.UserClickListener{

    private BookViewModel viewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<Book> books = new ArrayList<>();
    private List<FavouriteBook> favouriteBooks = new ArrayList<>();
    FavouriteAdapter favouriteAdapter;
    @BindView(R.id.favouriteListRecycler)
    RecyclerView favouriteListRecycler;

    private int x=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        ButterKnife.bind(this);

        viewModel = new ViewModelProvider(this).get(BookViewModel.class);
        //DebugLogger.logDebug(""+favouriteBooks.size());

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        favouriteListRecycler.setLayoutManager(new LinearLayoutManager(this));
        favouriteListRecycler.setAdapter(new FavouriteAdapter(books, this, this));
        favouriteListRecycler.addItemDecoration(itemDecoration);

        favouriteBooks.addAll(viewModel.getAllFavourites());
        findAllFavourite();

    }

    private void findAllFavourite(){
        if(favouriteBooks.size()>0) {
            for (int i = 0; i < favouriteBooks.size(); i++) {
                compositeDisposable.add(viewModel.getSpecificBookRx(favouriteBooks.get(i).getId()).subscribe(bookResult -> {

                    books.add(bookResult);
                    favouriteListRecycler.setAdapter(null);
                    favouriteAdapter = new FavouriteAdapter(books, this, this);
                    favouriteListRecycler.setAdapter(favouriteAdapter);
                    favouriteAdapter.notifyDataSetChanged();

                }, throwable -> {
                    DebugLogger.logError(throwable);

                }));
            }
        }

    }

    private void getNewFavourites(List<FavouriteBook> favouriteBooks){
        favouriteListRecycler.setAdapter(null);

        if(favouriteBooks.size()>0) {
            for (int i = 0; i < favouriteBooks.size(); i++) {
                compositeDisposable.add(viewModel.getSpecificBookRx(favouriteBooks.get(i).getId()).subscribe(bookResult -> {

                    books.add(bookResult);
                    favouriteAdapter = new FavouriteAdapter(books, this, this);
                    favouriteListRecycler.setAdapter(favouriteAdapter);
                    favouriteAdapter.notifyDataSetChanged();

                }, throwable -> {
                    DebugLogger.logError(throwable);

                }));
            }
        }

    }


    @OnClick(R.id.backToPrevious)
    public void backToPrevious(View view){
        compositeDisposable.dispose();
        finish();
    }

    @Override
    public void unfavouriteButton(Book book, int position)
    {
        viewModel.deleteAFavourite(book);
        //DebugLogger.logDebug("1: "+favouriteBooks.size());
        favouriteBooks.remove(position);
        //DebugLogger.logDebug("2: "+favouriteBooks.size());

        books.clear();
        getNewFavourites(favouriteBooks);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
