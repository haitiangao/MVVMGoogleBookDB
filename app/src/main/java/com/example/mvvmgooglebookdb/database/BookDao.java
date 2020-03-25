package com.example.mvvmgooglebookdb.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mvvmgooglebookdb.model.FavouriteBook;

import java.util.List;

@Dao
public interface BookDao {

    @Query("SELECT * FROM Favourite_Book")
    List<FavouriteBook> getFavouriteBooks();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAFavourite(FavouriteBook favouriteBook);

    @Query("DELETE FROM Favourite_Book WHERE id = :id")
    void deleteAFavourite(String id);

}
