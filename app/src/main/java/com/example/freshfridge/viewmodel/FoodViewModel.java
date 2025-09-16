package com.example.freshfridge.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.freshfridge.data.FoodItem;
import com.example.freshfridge.repository.FoodRepository;

import java.util.List;

public class FoodViewModel extends AndroidViewModel {
    private final FoodRepository repository;
    private final LiveData<List<FoodItem>> allFood;

    public FoodViewModel(@NonNull Application application) {
        super(application);
        repository = new FoodRepository(application);
        allFood = repository.getAllFood();
    }

    public LiveData<List<FoodItem>> getAllFood() {
        return allFood;
    }

    public void insert(FoodItem item) {
        repository.insert(item);
    }

    public void delete(FoodItem item) {
        repository.delete(item);
    }
}
