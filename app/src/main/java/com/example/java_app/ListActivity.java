package com.example.java_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.*;

public class ListActivity extends AppCompatActivity implements MyGridAdapter.OnItemClickListener {

    BottomNavigationView bottomNavigationView;

    private RecyclerView recyclerView;
    private List<Watch> watches = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Passing over items from previous activity
        String brandName = getIntent().getStringExtra("Brand Name");
        String watchType = getIntent().getStringExtra("Watch Type");
        String movementType = getIntent().getStringExtra("Movement Type");


        // Getting references to text views in layout
        TextView mainHeading = findViewById(R.id.title_heading);


        // Setting the heading of listActivity based on what has been passed
        mainHeading.setText(brandName != null ? brandName :
                            watchType != null ? watchType :
                            movementType);

        // Find the reference to the back button
        ImageButton backButton = findViewById(R.id.backButton);

        // Set a click listener to handle the back button functionality
        backButton.setOnClickListener(v -> onBackPressed());


        //Set up new RecyclerView
        watches = DataProvider.loadWatches(this);
        recyclerView = findViewById(R.id.recyclerview);

        //Grid layout
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new MyGridAdapter(ListActivity.this, filterWatches(brandName, watchType, movementType), this));


        // Navigation Bar
        bottomNavigationView = findViewById(R.id.bottomNavigationBar);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_favourites) {
                ListActivity.this.startActivity(new Intent(ListActivity.this, FavouritesActivity.class));
                return true;
            } else if (itemId == R.id.action_home) {
                ListActivity.this.startActivity(new Intent(ListActivity.this, MainActivity.class));
                return true;
            } else if (itemId == R.id.action_search) {
                ListActivity.this.startActivity(new Intent(ListActivity.this, SearchActivity.class));
                return true;
            }

            return false;
        });


    }

    //Click on a watch
    @Override
    public void onItemClick(Watch watch) {
        WatchClickTracker.trackClick(watch);
        Intent intent = new Intent(ListActivity.this, DetailsActivity.class);
        intent.putExtra("watch", watch);
        intent.putIntegerArrayListExtra("watch images", (ArrayList<Integer>) watch.getImageLinks());
        startActivity(intent);
    }

    //Filtering
    private List<Watch> filterWatches(String brandName, String watchType, String movementType) {
        List<Watch> filteredWatches = new ArrayList<>();

        for (Watch watch : watches) {
            if (brandName != null && watch.getBrand().equalsIgnoreCase(brandName)) {
                filteredWatches.add(watch);
            } else if (watchType != null && watch.getFunctionType().equalsIgnoreCase(watchType)) {
                filteredWatches.add(watch);
            } else if (movementType != null && watch.getMovementType().equalsIgnoreCase(movementType)) {
                filteredWatches.add(watch);
            }
        }

        return filteredWatches;
    }

}
