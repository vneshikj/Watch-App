package com.example.java_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MyGridAdapter.OnItemClickListener, BrandsRecyclerInterface, WatchTypesRecyclerInterface, MovementTypesRecyclerInterface  {

    private BottomNavigationView bottomNavigationView;

    private List<Watch> watches = new ArrayList<>();
    private ArrayList<Brand> brands = new ArrayList<>();
    private ArrayList<String> watchTypes = new ArrayList<>();
    private  ArrayList<String> movementTypes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Recycler view for top picks
        RecyclerView recyclerView1 = findViewById(R.id.topPicksRecyclerView);
        watches = DataProvider.loadWatches(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setAdapter(new MyGridAdapter(MainActivity.this,WatchClickTracker.getTopThreeWatches(watches), this));

        // Data source for brands
        brands.add(new Brand("G-Shock", R.drawable.gshock_logo));
        brands.add(new Brand("SEIKO", R.drawable.seiko_logo));
        brands.add(new Brand("Rolex", R.drawable.rolex_logo));


        // Recycler view for brands
        RecyclerView recyclerView2 = findViewById(R.id.BrandsRecyclerView);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerViewBrands.MyRvAdapter myRvAdapter1 = new RecyclerViewBrands.MyRvAdapter(brands, this);
        recyclerView2.setLayoutManager(linearLayoutManager1);
        recyclerView2.setAdapter(myRvAdapter1);


        // data source for Watch types
        watchTypes.add("Analog");
        watchTypes.add("Digital");


        // Recycler view for watch types
        RecyclerView recyclerView3 = findViewById(R.id.WatchTypesRecyclerView);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerViewWatchTypes.MyRvAdapter myRvAdapter2 = new RecyclerViewWatchTypes.MyRvAdapter(watchTypes, this);
        recyclerView3.setLayoutManager(linearLayoutManager2);
        recyclerView3.setAdapter(myRvAdapter2);

        // Data source for Movement Types

        movementTypes.add("Automatic");
        movementTypes.add("Quartz");

        // Recycler view for movement types
        RecyclerView recyclerView4 = findViewById(R.id.MovementTypesRecyclerView);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerViewMovementTypes.MyRvAdapter myRvAdapter3 = new RecyclerViewMovementTypes.MyRvAdapter(movementTypes, this);
        recyclerView4.setLayoutManager(linearLayoutManager3);
        recyclerView4.setAdapter(myRvAdapter3);


        // Navigation bar
        bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setSelectedItemId(R.id.action_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_favourites) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, FavouritesActivity.class));
               // overridePendingTransition(0, 0);
                return true;
            }
            else if (itemId == R.id.action_home) {
                return true;
            }

            else if (itemId == R.id.action_search) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, SearchActivity.class));
                //overridePendingTransition(0, 0);
                return true;
            }

            return false;
        });
    }

    @Override
    public void onBrandClick(int position) { // Swap from main to list activity
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        intent.putExtra("Brand Name", brands.get(position).getBrandName());

        startActivity(intent);
    }

    @Override
    public void onWatchTypeClick(int position) {
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        intent.putExtra("Watch Type", watchTypes.get(position));

        startActivity(intent);
    }

    @Override
    public void onMovementTypeClick(int position) {
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        intent.putExtra("Movement Type", movementTypes.get(position));

        startActivity(intent);
    }

    @Override
    public void onItemClick(Watch watch) {
        WatchClickTracker.trackClick(watch);
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("watch", watch);
        intent.putIntegerArrayListExtra("watch images", (ArrayList<Integer>) watch.getImageLinks());
        startActivity(intent);
    }
}
