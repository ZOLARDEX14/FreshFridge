package com.example.freshfridge.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodItemDao {

    @Insert
    void insert(FoodItem item);

    @Delete
    void delete(FoodItem item);

    @Query("SELECT * FROM food_table ORDER BY expiryDate ASC")
    LiveData<List<FoodItem>> getAll();
}
