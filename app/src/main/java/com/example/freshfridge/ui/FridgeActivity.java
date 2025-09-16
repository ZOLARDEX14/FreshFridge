package com.example.freshfridge.ui;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.freshfridge.R;

import java.util.Calendar;

public class FridgeActivity extends AppCompatActivity {

    private LinearLayout shelf1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        shelf1 = findViewById(R.id.shelf1);

        // ตั้ง listener ให้ชั้นวาง
        shelf1.setOnDragListener(dragListener);

        // ตั้งให้ไอคอนวัตถุดิบลากได้
        setupDraggable(R.id.item_milk, "นม");
        setupDraggable(R.id.item_meat, "เนื้อ");
        setupDraggable(R.id.item_lettuce, "ผัก");
    }

    private void setupDraggable(int viewId, String name) {
        ImageView item = findViewById(viewId);
        item.setTag(name);

        item.setOnLongClickListener(v -> {
            ClipData data = ClipData.newPlainText("label", name);
            View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
            v.startDragAndDrop(data, shadow, v, 0);
            return true;
        });
    }

    private final View.OnDragListener dragListener = (v, event) -> {
        switch (event.getAction()) {
            case DragEvent.ACTION_DROP:
                View draggedView = (View) event.getLocalState();
                ((ViewGroup) draggedView.getParent()).removeView(draggedView);

                // สร้างไอคอนใหม่
                ImageView copy = new ImageView(this);
                copy.setImageDrawable(((ImageView) draggedView).getDrawable());
                copy.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                String name = (String) draggedView.getTag();

                // เลือกวันหมดอายุ
                showExpiryDialog(name, copy);
                return true;
        }
        return true;
    };

    private void showExpiryDialog(String name, ImageView image) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            long millis = calendar.getTimeInMillis();
            long now = System.currentTimeMillis();
            long diff = (millis - now) / (1000 * 60 * 60 * 24);

            TextView label = new TextView(this);
            label.setText("D-" + diff);
            label.setTextSize(10);
            label.setTextColor(getColor(android.R.color.black));

            FrameLayout frame = new FrameLayout(this);
            frame.addView(image);
            frame.addView(label);

            shelf1.addView(frame);
            Toast.makeText(this, name + " จะหมดอายุใน D-" + diff, Toast.LENGTH_SHORT).show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
