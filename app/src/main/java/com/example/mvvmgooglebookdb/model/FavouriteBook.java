package com.example.mvvmgooglebookdb.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Favourite_Book")
public class FavouriteBook {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    public FavouriteBook(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
