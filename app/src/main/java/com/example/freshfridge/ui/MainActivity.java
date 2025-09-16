package com.example.freshfridge.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freshfridge.R;
import com.example.freshfridge.adapter.FoodAdapter;
import com.example.freshfridge.data.FoodItem;
import com.example.freshfridge.viewmodel.FoodViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FoodViewModel viewModel;
    private TextView tvWarning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // View setup
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        tvWarning = findViewById(R.id.tv_expiry_warning);
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        FloatingActionButton fabPlanner = findViewById(R.id.fab_planner);
        FloatingActionButton fabFridge = findViewById(R.id.fab_fridge);

        // Adapter
        final FoodAdapter adapter = new FoodAdapter(item -> viewModel.delete(item));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ViewModel
        viewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        viewModel.getAllFood().observe(this, foodItems -> {
            adapter.setFoodList(foodItems);
            checkExpiringItems(foodItems);
        });

        // ➕ เพิ่มอาหาร
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddFoodActivity.class);
            startActivity(intent);
        });

        // 📅 ไปหน้า Meal Planner
        fabPlanner.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MealPlannerActivity.class);
            startActivity(intent);
        });

        // 🧊 ไปหน้า FridgeActivity
        fabFridge.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FridgeActivity.class);
            startActivity(intent);
        });
    }

    // ⚠️ ตรวจสอบของใกล้หมดอายุ
    private void checkExpiringItems(List<FoodItem> list) {
        boolean hasExpiring = false;
        long now = System.currentTimeMillis();

        for (FoodItem item : list) {
            if (item.expiryDate - now <= 86400000 && item.expiryDate > now) {
                hasExpiring = true;
                break;
            }
        }

        tvWarning.setVisibility(hasExpiring ? View.VISIBLE : View.GONE);
    }
}
