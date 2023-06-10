package com.example.java_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class RecyclerViewBrands extends AppCompatActivity {
    RecyclerView rv;
    ArrayList<String> dataSource;
    LinearLayoutManager linearLayoutManager;
    MyRvAdapter myRvAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rv = findViewById(R.id.BrandsRecyclerView);


        /*// Setting the data source todo: data provider class here
        dataSource = new ArrayList<>();
        dataSource.add("Hello");
        dataSource.add("Hello");
        dataSource.add("Hello");
        dataSource.add("Hello");
        dataSource.add("Goodbye");



        linearLayoutManager = new LinearLayoutManager(RecyclerViewTopPicks.this, LinearLayoutManager.HORIZONTAL, false);
        myRvAdapter = new MyRvAdapter(dataSource);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(myRvAdapter);*/

    }


    static class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyHolder> {

        private final BrandsRecyclerInterface brandsRecyclerInterface;
        ArrayList<Brand> data;

        public MyRvAdapter(ArrayList<Brand> data, BrandsRecyclerInterface brandsRecyclerInterface) {
            this.data = data;
            this.brandsRecyclerInterface = brandsRecyclerInterface;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_card, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.brandLogo.setImageResource(data.get(position).getLogoLink());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            ImageView brandLogo;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                brandLogo = itemView.findViewById(R.id.brand_imageView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (brandsRecyclerInterface != null) {
                            int pos = getBindingAdapterPosition(); // todo: this could be an error here

                            if (pos != RecyclerView.NO_POSITION) {
                                brandsRecyclerInterface.onBrandClick(pos);
                            }
                        }
                    }
                });
            }
        }
    }

}
