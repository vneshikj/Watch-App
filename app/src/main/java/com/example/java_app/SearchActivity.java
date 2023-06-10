package com.example.java_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener {
    private BottomNavigationView bottomNavigationView;
    private List<Watch> watches = new ArrayList<>();
    private ArrayList<Watch> filteredWatches = new ArrayList<>();

    private RecyclerView recyclerView;
    private TextView textView;
    private TextView textNoWatchesFound;


    private Spinner spinnerBrand;
    private Spinner spinnerType;
    private Spinner spinnerMovement;

    private ArrayAdapter<String> brandsSpinnerAdapter;
    private ArrayAdapter<String> typeSpinnerAdapter;
    private ArrayAdapter<String> movementSpinnerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Getting data
        watches = DataProvider.loadWatches(this);

        // Setting recycler view
        recyclerView = findViewById(R.id.recyclerSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), watches, this));
        recyclerView.setHasFixedSize(true);

        //
        textNoWatchesFound = findViewById(R.id.no_watches_found);

        // Navigation bar
        bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setSelectedItemId(R.id.action_search);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_favourites) {
                SearchActivity.this.startActivity(new Intent(SearchActivity.this, FavouritesActivity.class));
                // overridePendingTransition(0, 0);
                return true;
            }
            else if (itemId == R.id.action_home) {
                SearchActivity.this.startActivity(new Intent(SearchActivity.this, MainActivity.class));
                // overridePendingTransition(0,0);
                return true;
            }
            else if (itemId == R.id.action_search) {
                return true;
            }

            return false;
        });

        initSearchWidgets();
        initBrandSpinner();
        initTypeSpinner();
        initMovementSpinner();
    }


    // Navigate to details activity depending on the item clicked
    @Override
    public void onItemClick(Watch watch) {
        WatchClickTracker.trackClick(watch);
        Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
        intent.putExtra("watch", watch);
        intent.putIntegerArrayListExtra("watch images", (ArrayList<Integer>) watch.getImageLinks());

        startActivity(intent);
    }


    private void initSearchWidgets() {
        SearchView watchSearchView = findViewById(R.id.watch_search_bar);
        spinnerBrand = findViewById(R.id.spinner_brand);
        spinnerType = findViewById(R.id.spinner_type);
        spinnerMovement = findViewById(R.id.spinner_movement);
        textView = findViewById(R.id.filter_by_text);

        // Enable the search bar to expand when clicked
        watchSearchView.setOnClickListener(v -> {
            watchSearchView.onActionViewExpanded();
            hideSpinnersAndTextView();
        });

        watchSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // Called every time the user changes the text
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    showSpinnersAndTextView();
                    textNoWatchesFound.setVisibility(View.GONE); // Hide the message
                } else {
                    hideSpinnersAndTextView();

                    ArrayList<Watch> filteredWatches = new ArrayList<>();

                    for (Watch watch : watches) {
                        if (watch.getName().toLowerCase().contains(newText.toLowerCase())) {
                            filteredWatches.add(watch);
                        }
                    }

                    recyclerView.setAdapter(new MyAdapter(getApplicationContext(), filteredWatches, SearchActivity.this));

                    // Show the "No watches found" message if no watches match the search query
                    if (filteredWatches.isEmpty()) {
                        textNoWatchesFound.setVisibility(View.VISIBLE);
                    } else {
                        textNoWatchesFound.setVisibility(View.GONE); // Hide the message if there are matching watches
                    }
                }

                return false;
            }

        });

        watchSearchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                hideSpinnersAndTextView();
            } else {
                showSpinnersAndTextView();
            }
        });
    }

    void initBrandSpinner() {
        spinnerBrand = findViewById(R.id.spinner_brand);
        brandsSpinnerAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, getCustomBrands(), R.color.orange_accent);
        brandsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBrand.setAdapter(brandsSpinnerAdapter);

        spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedBrand = parent.getItemAtPosition(position).toString();


                for (Watch watch : watches) {
                    if (watch.getBrand().equalsIgnoreCase(selectedBrand)) {
                        filteredWatches.add(watch);
                    }
                }

                applyFilters();

                recyclerView.setAdapter(new MyAdapter(getApplicationContext(), filteredWatches, SearchActivity.this));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    void initTypeSpinner() {
        spinnerType = findViewById(R.id.spinner_type);
        typeSpinnerAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, getCustomTypes(), R.color.orange_accent);
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeSpinnerAdapter);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = parent.getItemAtPosition(position).toString();


                for (Watch watch : watches) {
                    if (watch.getFunctionType().equalsIgnoreCase(selectedType)) {
                        filteredWatches.add(watch);
                    }
                }

                applyFilters();

                recyclerView.setAdapter(new MyAdapter(getApplicationContext(), filteredWatches, SearchActivity.this));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }


    void initMovementSpinner() {
        spinnerMovement = findViewById(R.id.spinner_movement);
        movementSpinnerAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, getCustomMovements(), R.color.orange_accent);
        movementSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMovement.setAdapter(movementSpinnerAdapter);

        spinnerMovement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedMovement = parent.getItemAtPosition(position).toString();


                for (Watch watch : watches) {
                    if (watch.getMovementType().equalsIgnoreCase(selectedMovement)) {
                        filteredWatches.add(watch);
                    }
                }

                applyFilters();

                recyclerView.setAdapter(new MyAdapter(getApplicationContext(), filteredWatches, SearchActivity.this));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void hideSpinnersAndTextView() {
        spinnerBrand.setVisibility(View.GONE);
        spinnerType.setVisibility(View.GONE);
        spinnerMovement.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
    }

    private void showSpinnersAndTextView() {
        spinnerBrand.setVisibility(View.VISIBLE);
        spinnerType.setVisibility(View.VISIBLE);
        spinnerMovement.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
    }

    private void applyFilters() {
        // Get the selected filter values from the spinners
        String selectedBrand = spinnerBrand.getSelectedItem().toString();
        String selectedType = spinnerType.getSelectedItem().toString();
        String selectedMovement = spinnerMovement.getSelectedItem().toString();

        // Clear the filteredWatches list to start fresh
        filteredWatches.clear();

        // Apply the filters based on the selected values
        for (Watch watch : watches) {
            boolean brandMatch = selectedBrand.equalsIgnoreCase("Brands") || watch.getBrand().equalsIgnoreCase(selectedBrand);
            boolean typeMatch = selectedType.equalsIgnoreCase("Types") || watch.getFunctionType().equalsIgnoreCase(selectedType);
            boolean movementMatch = selectedMovement.equalsIgnoreCase("Movements") || watch.getMovementType().equalsIgnoreCase(selectedMovement);

            if (brandMatch && typeMatch && movementMatch) {
                filteredWatches.add(watch);
            }
        }

        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), filteredWatches, SearchActivity.this));

        // Show the "No watches found" message if no watches match the filters
        if (filteredWatches.isEmpty()) {
            textNoWatchesFound.setVisibility(View.VISIBLE);
        } else {
            textNoWatchesFound.setVisibility(View.GONE);
        }
    }


    private List<String> getCustomBrands() {
        List<String> brands = new ArrayList<>();
        brands.add("Brands");
        brands.add("G-Shock");
        brands.add("Seiko");
        brands.add("Rolex");
        return brands;
    }

    private List<String> getCustomTypes() {
        List<String> types= new ArrayList<>();
        types.add("Types");
        types.add("Analog");
        types.add("Digital");
        return types;
    }

    private List<String> getCustomMovements() {
        List<String> movements= new ArrayList<>();
        movements.add("Movements");
        movements.add("Quartz");
        movements.add("Automatic");
        return movements;
    }


}