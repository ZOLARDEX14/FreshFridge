package com.example.freshfridge.adapter;

import android.graphics.Color;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freshfridge.R;
import com.example.freshfridge.data.FoodItem;

import java.text.SimpleDateFormat;
import java.util.*;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<FoodItem> foodList = new ArrayList<>();
    private final OnDeleteClickListener deleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(FoodItem item);
    }

    public FoodAdapter(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodItem item = foodList.get(position);
        holder.name.setText(item.name);
        holder.date.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(item.expiryDate)));

        long now = System.currentTimeMillis();
        if (item.expiryDate < now) {
            holder.status.setText("กินหมดแล้ว");
            holder.status.setBackgroundColor(Color.parseColor("#FF5C5C"));
        } else if (item.expiryDate - now <= 86400000) {
            holder.status.setText("ใกล้หมดอายุ");
            holder.status.setBackgroundColor(Color.parseColor("#FFD54F"));
        } else {
            holder.status.setText("");
            holder.status.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.delete.setOnClickListener(v -> deleteClickListener.onDeleteClick(item));
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public void setFoodList(List<FoodItem> list) {
        this.foodList = list;
        notifyDataSetChanged();
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, status;
        ImageButton delete;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            date = itemView.findViewById(R.id.tv_date);
            status = itemView.findViewById(R.id.tv_status);
            delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
