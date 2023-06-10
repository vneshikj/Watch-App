package com.example.java_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<Watch> watches;
    private OnItemClickListener itemClickListener;
    private List<Watch> favouriteWatches;

    public MyAdapter(Context context, List<Watch> watches, OnItemClickListener itemClickListener) {
        this.context = context;
        this.watches = watches;
        this.itemClickListener = itemClickListener;
        this.favouriteWatches = FavouritesManager.favouriteWatches;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.watch_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Watch watch = watches.get(position);
        holder.nameView.setText(watch.getName());
        holder.priceView.setText("$" + watch.getPrice() + "0");
        holder.imageView.setImageResource(watch.getImageLinks().get(0));

        if (favouriteWatches.contains(watch)) {
            holder.favourite_button.setChecked(true);
        } else {
            holder.favourite_button.setChecked(false);
        }

        // Set an OnClickListener on the checkbox
        holder.favourite_button.setOnClickListener(v -> {
            boolean isChecked = holder.favourite_button.isChecked();
            if (isChecked) {
                FavouritesManager.addToFavourites(watch);
            } else {
                FavouritesManager.removeFromFavourites(watch);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(watch);
            }
        });
    }

    @Override
    public int getItemCount() {
        return watches.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Watch watch);
    }
}
