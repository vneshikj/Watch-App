package com.example.java_app;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView nameView, brandView, priceView;
    CheckBox favourite_button;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.watch_image);
        nameView = itemView.findViewById((R.id.watch_name));
        priceView = itemView.findViewById((R.id.watch_price));
        favourite_button = itemView.findViewById(R.id.quick_favorite_button);
    }
}
