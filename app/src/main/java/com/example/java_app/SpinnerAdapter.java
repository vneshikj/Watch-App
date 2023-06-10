package com.example.java_app;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private int textColor;

    public SpinnerAdapter(Context context, int resource, List<String> items, int textColor) {
        super(context, resource, items);
        this.context = context;
        this.textColor = textColor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTextColor(ContextCompat.getColor(context, textColor));
        view.setGravity(Gravity.CENTER);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setTextColor(ContextCompat.getColor(context, textColor));
        view.setGravity(Gravity.CENTER);
        return view;
    }
}
