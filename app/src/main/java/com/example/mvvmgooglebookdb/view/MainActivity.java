package com.example.mvvmgooglebookdb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.mvvmgooglebookdb.R;
import com.example.mvvmgooglebookdb.adapter.BookAdapter;
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

import static com.example.mvvmgooglebookdb.util.DebugLogger.logDebug;

public class MainActivity extends AppCompatActivity implements BookAdapter.UserClickListener{

    private BookViewModel viewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String query;
    private List<Book> books = new ArrayList<>();

    @BindView(R.id.bookListRecycler)
    RecyclerView bookListRecycler;
    @BindView(R.id.searchBookEdit)
    EditText searchBookEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        viewModel = new ViewModelProvider(this).get(BookViewModel.class);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        bookListRecycler.setLayoutManager(new LinearLayoutManager(this));
        bookListRecycler.setAdapter(new BookAdapter(books, this, this));
        bookListRecycler.addItemDecoration(itemDecoration);

    }

    @OnClick(R.id.searchBookButton)
    public void searchBooks(){
        query =searchBookEdit.getText().toString();

        compositeDisposable.add(viewModel.getBookListRx(query).subscribe(resultLibrary -> {
            List<Book> bookResults = resultLibrary.getItems();
            BookAdapter bookAdapter = new BookAdapter(bookResults, this, this);
            bookListRecycler.setAdapter(null);
            bookListRecycler.setAdapter(bookAdapter);
            bookAdapter.notifyDataSetChanged();

        }, throwable -> {

            DebugLogger.logError(throwable);

        }));

    }

    @Override
    protected void onResume() {
        super.onResume();
        searchBooks();
    }

    @OnClick(R.id.seeFavouriteButton)
    public void seeAllFavourite(){
        Intent intent = new Intent(this, FavouriteActivity.class);
        startActivity(intent);
    }

    @Override
    public void favouriteButton(Book book) {
        viewModel.insertAFavourite(book);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        viewModel.disposeDisposables();

    }
}
