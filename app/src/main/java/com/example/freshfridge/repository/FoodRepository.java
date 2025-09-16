package com.example.freshfridge.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.freshfridge.data.*;
import java.util.List;
import java.util.concurrent.Executors;

public class FoodRepository {
    private final FoodDao foodDao;
    private final LiveData<List<FoodItem>> allFood;

    public FoodRepository(Application application) {
        FoodDatabase db = FoodDatabase.getDatabase(application);
        foodDao = db.foodDao();
        allFood = foodDao.getAllFood();
    }

    public LiveData<List<FoodItem>> getAllFood() {
        return allFood;
    }

    public void insert(FoodItem item) {
        Executors.newSingleThreadExecutor().execute(() -> foodDao.insert(item));
    }

    public void delete(FoodItem item) {
        Executors.newSingleThreadExecutor().execute(() -> foodDao.delete(item));
    }
}
