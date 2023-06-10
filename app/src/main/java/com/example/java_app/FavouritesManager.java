package com.example.java_app;

import android.util.Log;

import java.util.*;

public class FavouritesManager {
    public static List<Watch> favouriteWatches = new ArrayList<>();

    public static void addToFavourites(Watch watch) {
        if (!favouriteWatches.contains(watch)){
            watch.setFavorite(true);
            favouriteWatches.add(watch);
        }
    }

    public static void removeFromFavourites(Watch watch) {
        watch.setFavorite(false);
        favouriteWatches.remove(watch);
    }

    public static int listSize(){return favouriteWatches.size();
    }
}
