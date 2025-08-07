package com.example.feels;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.ViewHolder> {

    private static final String TAG = "CarouselAdapter";
    private Context context;
    private List<CarouselItem> itemList;

    public CarouselAdapter(Context context, List<CarouselItem> itemList) {
        this.context = context;
        this.itemList = itemList != null ? itemList : new ArrayList<>();
    }

    public void setItems(List<CarouselItem> items) {
        this.itemList = items != null ? items : new ArrayList<>();
        Log.d(TAG, "setItems called with " + this.itemList.size() + " items");
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carousel_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CarouselItem item = itemList.get(position);

        Log.d(TAG, "Binding item at position " + position + ": " + item.getTitle());

        holder.itemTitle.setText(item.getTitle());
        holder.itemSubtitle.setText(item.getSubtitle());
        holder.itemDescription.setText(item.getDescription());

        // Set background color based on mood
        try {
            GradientDrawable background = (GradientDrawable) holder.itemLayout.getBackground();
            if (background != null) {
                int colorRes = getColorForMood(item.getSubtitle());
                background.setColor(ContextCompat.getColor(holder.itemView.getContext(), colorRes));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error setting background color", e);
        }
    }

    private int getColorForMood(String mood) {
        if (mood == null) return R.color.default_color;

        switch (mood.trim()) {
            case "Very Negative": return R.color.very_negative_color;
            case "Negative": return R.color.negative_color;
            case "Neutral": return R.color.neutral_color;
            case "Positive": return R.color.positive_color;
            case "Very Positive": return R.color.very_positive_color;
            default:
                Log.d(TAG, "Unknown mood: " + mood + ", using default color");
                return R.color.default_color;
        }
    }

    @Override
    public int getItemCount() {
        int count = itemList != null ? itemList.size() : 0;
        Log.d(TAG, "getItemCount: " + count);
        return count;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle;
        TextView itemSubtitle;
        TextView itemDescription;
        View itemLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemSubtitle = itemView.findViewById(R.id.itemSubtitle);
            itemDescription = itemView.findViewById(R.id.itemDescription);
            itemLayout = itemView.findViewById(R.id.carouselItem);
        }
    }
}