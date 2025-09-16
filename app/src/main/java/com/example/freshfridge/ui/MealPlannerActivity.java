package com.example.freshfridge.ui;

import android.os.Bundle;
import android.widget.*;
import android.app.AlertDialog;
import android.text.InputType;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.freshfridge.R;
import com.example.freshfridge.data.MealPlan;
import com.example.freshfridge.viewmodel.MealPlanViewModel;

import java.text.SimpleDateFormat;
import java.util.*;

public class MealPlannerActivity extends AppCompatActivity {

    private MealPlanViewModel viewModel;
    private ListView lvPlans;
    private long selectedDateMillis;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_planner);

        CalendarView calendarView = findViewById(R.id.calendarView);
        lvPlans = findViewById(R.id.list_meals);
        Button btnSuggest = findViewById(R.id.btn_suggest);
        Button btnAdd = findViewById(R.id.btn_add);

        viewModel = new ViewModelProvider(this).get(MealPlanViewModel.class);

        // วันที่เริ่มต้น: วันนี้
        selectedDateMillis = calendarView.getDate();
        loadPlans(selectedDateMillis);

        // เปลี่ยนวันในปฏิทิน
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDateMillis = getDateMillis(year, month, dayOfMonth);
            loadPlans(selectedDateMillis);
        });

        // ปุ่ม "แนะนำเมนูวันนี้"
        btnSuggest.setOnClickListener(v -> suggestMeal());

        // ปุ่ม "เพิ่มเมนูด้วยตัวเอง"
        btnAdd.setOnClickListener(v -> showAddMealDialog());
    }

    private void loadPlans(long dateMillis) {
        String formattedDate = formatDate(dateMillis);

        viewModel.getPlansByDate(formattedDate).observe(this, mealPlans -> {
            List<String> display = new ArrayList<>();
            for (MealPlan p : mealPlans) {
                display.add("🍽️ " + p.meal);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, display);
            lvPlans.setAdapter(adapter);
        });
    }

    private void suggestMeal() {
        // คุณสามารถเพิ่มระบบสุ่มเมนูได้เอง
        String[] meals = {"ข้าวผัดหมู", "แกงเขียวหวาน", "ผัดกระเพราไก่", "ต้มยำกุ้ง", "ผัดไทย"};
        String suggestedMeal = meals[new Random().nextInt(meals.length)];

        String date = formatDate(selectedDateMillis);
        MealPlan plan = new MealPlan(date, suggestedMeal);
        viewModel.insert(plan);

        Toast.makeText(this, "เพิ่มเมนู: " + suggestedMeal, Toast.LENGTH_SHORT).show();
        loadPlans(selectedDateMillis);
    }

    private void showAddMealDialog() {
        EditText input = new EditText(this);
        input.setHint("ใส่ชื่อเมนู");
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        new AlertDialog.Builder(this)
                .setTitle("เพิ่มเมนูด้วยตัวเอง")
                .setView(input)
                .setPositiveButton("เพิ่ม", (dialog, which) -> {
                    String meal = input.getText().toString().trim();
                    if (!meal.isEmpty()) {
                        String date = formatDate(selectedDateMillis);
                        MealPlan plan = new MealPlan(date, meal);
                        viewModel.insert(plan);
                        loadPlans(selectedDateMillis);
                    }
                })
                .setNegativeButton("ยกเลิก", null)
                .show();
    }

    private String formatDate(long millis) {
        return dateFormat.format(new Date(millis));
    }

    private long getDateMillis(int y, int m, int d) {
        Calendar c = Calendar.getInstance();
        c.set(y, m, d, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }
}
