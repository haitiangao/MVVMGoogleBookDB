package com.example.mvvmgooglebookdb.adapter;

import android.content.Context;
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

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>  {
    private List<Book> books;
    private Context context;
    private UserClickListener userClickListener;

    public FavouriteAdapter(List<Book> books, Context context, UserClickListener userClickListener) {
        this.books = books;
        this.context = context;
        this.userClickListener = userClickListener;
    }

    public interface UserClickListener {
        void unfavouriteButton(Book book);
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourite_list_item,parent,false);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
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
        holder.unfavouriteButton.setOnClickListener(view ->
                userClickListener.unfavouriteButton(books.get(position)));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }




    public class FavouriteViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumbNailF)
        ImageView thumbNailView;
        @BindView(R.id.titleF)
        TextView titleView;
        @BindView(R.id.authorF)
        TextView authorView;
        @BindView(R.id.unfavouriteThis)
        Button unfavouriteButton;

        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
