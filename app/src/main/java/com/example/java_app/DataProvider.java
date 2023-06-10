package com.example.java_app;

import android.app.Instrumentation;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.example.java_app.Watch;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



public class DataProvider {
    private static final String TAG = DataProvider.class.getSimpleName();

    public static List<Watch> loadWatches(Context context) {
        List<Watch> watches = new ArrayList<>();

        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            // Load JSON data from assets directory
            inputStream = assetManager.open("watches.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");

            // Parse JSON array
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject watchJson = jsonArray.getJSONObject(i);

                // Extract watch details from JSON object
                String name = watchJson.getString("name");
                String description = watchJson.getString("description");
                Double price = watchJson.getDouble("price");
                String brand = watchJson.getString("brand");
                String movementType = watchJson.getString("movementType");
                String functionType = watchJson.getString("functionType");
                JSONArray imageArray = watchJson.getJSONArray("images");
                boolean favorite = watchJson.getBoolean("favourited");
                Double searches = watchJson.getDouble("searches");



                List<Integer> imageLinks = new ArrayList<>();

                // Load watch images from drawable directory
                for (int j = 0; j < imageArray.length(); j++) {
                    String imageFileName = imageArray.getString(j);
                    int resourceId = getResourceId(context, imageFileName);
                    if (resourceId != 0) {
                        imageLinks.add(resourceId);
                    }
                }

                // Create Watch object and add to list
                Watch watch = new Watch(name, description, price, brand, movementType, functionType, imageLinks, favorite, searches);
                watches.add(watch);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error loading JSON file: " + e.getMessage());
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON data: " + e.getMessage());
        }

        return watches;
    }

    private static int getResourceId(Context context, String resourceName) {
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(resourceName, "drawable", context.getPackageName());
        if (resourceId == 0) {
            Log.e(TAG, "Resource not found: " + resourceName);
        }
        return resourceId;
    }
}
