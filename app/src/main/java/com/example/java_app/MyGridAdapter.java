package com.example.java_app;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.*;

public class MyGridAdapter extends RecyclerView.Adapter<MyGridAdapter.WatchHolder>
{

    private Context context;
    private List<Watch> watchList;
    private List<Watch> favouriteWatches;
    private OnItemClickListener itemClickListener;
    public MyGridAdapter(Context context, List<Watch> watches,  OnItemClickListener itemClickListener){
        this.context = context;
        this.watchList = watches;
        this.favouriteWatches = FavouritesManager.favouriteWatches;
        this.itemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public WatchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.redone_watch_item, parent, false);
        return new WatchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchHolder holder, int position) {

        Watch watch = watchList.get(position);
        holder.watchPrice.setText("$" + watch.getPrice());
        holder.watchName.setText(watch.getName());
        Glide.with(context).load(watch.getImageLinks().get(0)).into(holder.image);

        if (favouriteWatches.contains(watch)) {
            holder.favourites_button.setChecked(true);
        } else {
            holder.favourites_button.setChecked(false);
        }


        holder.favourites_button.setOnClickListener(v -> {
            boolean isChecked = holder.favourites_button.isChecked();
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
        return watchList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Watch watch);

    }

    public class WatchHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView watchName, watchPrice;
        CheckBox favourites_button;

        public WatchHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.watch_card_image);
            watchName = itemView.findViewById(R.id.watch_card_name);
            watchPrice = itemView.findViewById(R.id.watch_card_price);
            favourites_button = itemView.findViewById(R.id.quick_favorite_button);
        }
    }
}
