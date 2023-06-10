package com.example.java_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class FavouritesActivity extends AppCompatActivity implements MyGridAdapter.OnItemClickListener{
    BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        for (Watch w : FavouritesManager.favouriteWatches) {
            Log.d("FavouritesActivity", "Watch Name: " + w.getName());
            // Add more log statements for other properties if needed
        }

        recyclerView = findViewById(R.id.favourites_recyclerview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new MyGridAdapter(FavouritesActivity.this, FavouritesManager.favouriteWatches, this));



        // Navigation bar
        bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setSelectedItemId(R.id.action_favourites);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_favourites) {
                return true;
            }
            else if (itemId == R.id.action_home) {
                FavouritesActivity.this.startActivity(new Intent(FavouritesActivity.this, MainActivity.class));
               // overridePendingTransition(0,0);
                return true;
            }
            else if (itemId == R.id.action_search) {
                FavouritesActivity.this.startActivity(new Intent(FavouritesActivity.this, SearchActivity.class));
                //overridePendingTransition(0, 0);
                return true;
            }

            return false;
        });
    }

    @Override
    public void onItemClick(Watch watch) {
        WatchClickTracker.trackClick(watch);
        Intent intent = new Intent(FavouritesActivity.this, DetailsActivity.class);
        intent.putExtra("watch", watch);
        intent.putIntegerArrayListExtra("watch images", (ArrayList<Integer>) watch.getImageLinks());

        startActivity(intent);

    }
}