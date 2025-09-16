package com.example.freshfridge.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.freshfridge.R;
import com.example.freshfridge.data.FoodItem;
import com.example.freshfridge.util.NotificationUtils;
import com.example.freshfridge.viewmodel.FoodViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddFoodActivity extends AppCompatActivity {

    private long selectedDate = System.currentTimeMillis();
    private FoodViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        EditText etName = findViewById(R.id.et_food_name);
        Button btnPickDate = findViewById(R.id.btn_pick_date);
        Button btnSave = findViewById(R.id.btn_save);

        viewModel = new ViewModelProvider(this).get(FoodViewModel.class);

        // 🗓️ Date Picker
        btnPickDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(AddFoodActivity.this, (view, y, m, d) -> {
                Calendar picked = Calendar.getInstance();
                picked.set(y, m, d);
                selectedDate = picked.getTimeInMillis();

                String dateText = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(selectedDate));
                btnPickDate.setText(dateText);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        // ✅ Save
        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            if (!name.isEmpty()) {
                FoodItem item = new FoodItem(name, selectedDate);
                viewModel.insert(item);
                NotificationUtils.scheduleNotification(this, item);
                finish();
            } else {
                etName.setError("กรุณากรอกชื่ออาหาร");
            }
        });
    }
}
