package com.example.feels;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.ViewHolder> {

    private Context context;
    private List<CarouselItem> itemList;

    public CarouselAdapter(Context context, List<CarouselItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle;
        TextView itemSubtitle;
        TextView itemDescription;
        LinearLayout itemLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemSubtitle = itemView.findViewById(R.id.itemSubtitle);
            itemDescription = itemView.findViewById(R.id.itemDescription);
            itemLayout = itemView.findViewById(R.id.carouselItem);
        }
    }

    @Override
    public CarouselAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carousel_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CarouselAdapter.ViewHolder holder, int position) {
        CarouselItem item = itemList.get(position);
        holder.itemTitle.setText(item.getTitle());
        holder.itemSubtitle.setText(item.getSubtitle());
        holder.itemDescription.setText(item.getDescription());

        GradientDrawable background = (GradientDrawable) holder.itemLayout.getBackground();

        switch (item.getSubtitle()) {
            case "Very Negative":
                background.setColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.very_negative_color));
                break;
            case "Negative":
                background.setColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.negative_color));
                break;
            case "Neutral":
                background.setColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.neutral_color));
                break;
            case "Positive":
                background.setColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.positive_color));
                break;
            case "Very Positive":
                background.setColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.very_positive_color));
                break;
            default:
                background.setColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.default_color));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
