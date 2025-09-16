package com.example.freshfridge.data;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface FoodDao {
    @Query("SELECT * FROM food_table ORDER BY expiryDate ASC")
    LiveData<List<FoodItem>> getAllFood();

    @Insert
    void insert(FoodItem item);

    @Delete
    void delete(FoodItem item);
}
