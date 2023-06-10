package     com.example.java_app;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements MyGridAdapter.OnItemClickListener{

    BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private List<Watch> watches = new ArrayList<>();
    private Watch watch;
    private List<Integer> watchImages;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        watches = DataProvider.loadWatches(this);

        Watch watch = (Watch) getIntent().getSerializableExtra("watch");
        TextView watchTitle = findViewById(R.id.details_watch_title);
        watchTitle.setText(watch.getName());

        TextView priceTitle = findViewById(R.id.details_price_title);
        priceTitle.setText( '$' + String.valueOf(watch.getPrice()));

        TextView description = findViewById(R.id.description_content);
        description.setText(watch.getDescription());

        TextView brand = findViewById(R.id.brand_chip);
        brand.setText(watch.getBrand());

        TextView type = findViewById(R.id.type_chip);
        type.setText(watch.getFunctionType());

        TextView movement = findViewById(R.id.movement_chip);
        movement.setText(watch.getMovementType());

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());


        Button favoriteButton = findViewById(R.id.favoriteButton);

        favoriteButton.setBackgroundColor(watch.getFavorite() ? Color.WHITE : Color.BLACK);
        favoriteButton.setOnClickListener(v -> {
            boolean prevChecked = watch.getFavorite();
            if (prevChecked) {
                favoriteButton.setBackgroundColor(Color.BLACK);
                FavouritesManager.removeFromFavourites(watch);
            } else {
                favoriteButton.setBackgroundColor(Color.WHITE);
                FavouritesManager.addToFavourites(watch);

            }
        });

        // Recycler view for top picks
        RecyclerView recyclerView1 = findViewById(R.id.topPicksRecyclerView);
        watches = DataProvider.loadWatches(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setAdapter(new MyGridAdapter(DetailsActivity.this, WatchClickTracker.getTopThreeWatches(watches), this));


        // Navigation Bar
        bottomNavigationView = findViewById(R.id.bottomNavigationBar);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_favourites) {
                DetailsActivity.this.startActivity(new Intent(DetailsActivity.this, FavouritesActivity.class));
                return true;
            } else if (itemId == R.id.action_home) {
                DetailsActivity.this.startActivity(new Intent(DetailsActivity.this, MainActivity.class));
                return true;
            } else if (itemId == R.id.action_search) {
                DetailsActivity.this.startActivity(new Intent(DetailsActivity.this, SearchActivity.class));
                return true;
            }

            return false;
        });


        watchImages = getIntent().getIntegerArrayListExtra("watch images");

        initViewPager();
    }


    private void initViewPager() {
        viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(DetailsActivity.this, (ArrayList<Integer>) watchImages);
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(0);
    }

    @Override
    public void onItemClick(Watch watch) {
        Intent intent = new Intent(DetailsActivity.this, DetailsActivity.class);
        intent.putExtra("watch", watch);
        intent.putIntegerArrayListExtra("watch images", (ArrayList<Integer>) watch.getImageLinks());
        startActivity(intent);
    }
}