package com.example.java_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class RecyclerViewTopPicks extends AppCompatActivity {
    RecyclerView rv;
    ArrayList<String> dataSource;
    LinearLayoutManager linearLayoutManager;
    MyRvAdapter myRvAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rv = findViewById(R.id.topPicksRecyclerView);


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

        ArrayList<String> data;

        public MyRvAdapter(ArrayList<String> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.watch_item, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.watchName.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            TextView watchName;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                watchName = itemView.findViewById(R.id.watch_name_and_price);
            }
        }
    }

}
