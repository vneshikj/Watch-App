package com.example.java_app;

import java.util.*;

public class WatchClickTracker {
    private static Map<Watch, Integer> clickCounts = new HashMap<>();;

    public static void trackClick(Watch watch) {
        if (clickCounts.containsKey(watch)) {
            // Watch already exists in the map, increment the click count
            int currentClickCount = clickCounts.get(watch);
            clickCounts.put(watch, currentClickCount + 1);
        } else {
            // Watch is clicked for the first time, add it to the map with initial click count of 1
            clickCounts.put(watch, 1);
        }
    }

    public int getClickCount(Watch watch) {
        return clickCounts.getOrDefault(watch, 0);
    }

    public static List<Watch> getTopThreeWatches(List<Watch> initialWatches) {
        List<Map.Entry<Watch, Integer>> entries = new ArrayList<>(clickCounts.entrySet());

        // Sort the entries based on click counts in descending order
        Collections.sort(entries, new Comparator<Map.Entry<Watch, Integer>>() {
            @Override
            public int compare(Map.Entry<Watch, Integer> entry1, Map.Entry<Watch, Integer> entry2) {
                return entry2.getValue().compareTo(entry1.getValue());
            }
        });

        List<Watch> topThreeWatches = new ArrayList<>();

        for (Map.Entry<Watch, Integer> entry : entries) {
            Watch watch = entry.getKey();

            if (!topThreeWatches.contains(watch) && topThreeWatches.size() < 3) {
                topThreeWatches.add(watch);
            }

            if (topThreeWatches.size() >= 3) {
                break;
            }
        }

        // If there are less than three unique watches, add random watches from the initial list
        int remainingCount = 3 - topThreeWatches.size();
        if (remainingCount > 0) {
            List<Watch> randomWatches = getRandomWatches(initialWatches, remainingCount, topThreeWatches);
            topThreeWatches.addAll(randomWatches);
        }

        return topThreeWatches;
    }

    private static List<Watch> getRandomWatches(List<Watch> initialWatches, int count, List<Watch> excludeList) {
        List<Watch> randomWatches = new ArrayList<>();

        List<Watch> eligibleWatches = new ArrayList<>();
        for (Watch watch : initialWatches) {
            if (!excludeList.contains(watch)) {
                eligibleWatches.add(watch);
            }
        }

        // Shuffle the eligible watches list
        Collections.shuffle(eligibleWatches);

        // Add the first 'count' number of watches from the shuffled list
        for (int i = 0; i < count && i < eligibleWatches.size(); i++) {
            randomWatches.add(eligibleWatches.get(i));
        }

        return randomWatches;
    }



}

