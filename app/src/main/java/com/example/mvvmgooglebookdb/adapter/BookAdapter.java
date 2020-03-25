package com.example.mvvmgooglebookdb.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mvvmgooglebookdb.R;
import com.example.mvvmgooglebookdb.model.Book;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mvvmgooglebookdb.util.DebugLogger.logDebug;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>  {
    private List<Book> books;
    private Context context;
    private UserClickListener userClickListener;

    public BookAdapter(List<Book> books, Context context, UserClickListener userClickListener) {
        this.books = books;
        this.context = context;
        this.userClickListener = userClickListener;
    }

    public interface UserClickListener {
        void favouriteButton(Book book);
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_list_item,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        String authors="N/A";
        int authorNumber = books.get(position).getVolumeInfo().getAuthors().size();
        if (authorNumber>0) {
            authors="";
            for (int i = 0; i < authorNumber - 1; i++) {
                authors += books.get(position).getVolumeInfo().getAuthors().get(i) + ", ";
            }
            authors += books.get(position).getVolumeInfo().getAuthors().get(authorNumber-1);
        }

        holder.authorView.setText(authors);
        holder.titleView.setText(books.get(position).getVolumeInfo().getTitle());

        Glide.with(context)
                .asBitmap()
                .load(books.get(position).getVolumeInfo().getImageLinks().getThumbnail())
                .placeholder(R.drawable.ic_broken_image_black_24dp)
                .into(holder.thumbNailView);
        holder.favouriteButton.setOnClickListener(view ->
                userClickListener.favouriteButton(books.get(position)));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }




    public class BookViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbNail)
        ImageView thumbNailView;
        @BindView(R.id.title)
        TextView titleView;
        @BindView(R.id.author)
        TextView authorView;
        @BindView(R.id.favouriteThis)
        Button favouriteButton;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
