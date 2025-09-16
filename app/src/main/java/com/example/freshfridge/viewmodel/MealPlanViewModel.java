package com.example.freshfridge.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.freshfridge.data.AppDatabase;
import com.example.freshfridge.data.MealPlan;
import com.example.freshfridge.data.MealPlanDao;

import java.util.List;

public class MealPlanViewModel extends AndroidViewModel {

    private final MealPlanDao dao;

    public MealPlanViewModel(@NonNull Application application) {
        super(application);
        dao = AppDatabase.getDatabase(application).mealPlanDao();
    }

    public void insert(MealPlan plan) {
        AppDatabase.databaseWriteExecutor.execute(() -> dao.insert(plan));
    }

    public void delete(MealPlan plan) {
        AppDatabase.databaseWriteExecutor.execute(() -> dao.delete(plan));
    }

    public LiveData<List<MealPlan>> getPlansByDate(String date) {
        return dao.getMealsForDate(date);
    }
}
