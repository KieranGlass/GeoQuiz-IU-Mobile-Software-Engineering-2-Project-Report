package com.example.geoquiz;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class ResourceUtilities {

    public static int getLandmarkResourceId (String ignoredKey, String difficultyLevel){

        String tileMapName = difficultyLevel + "_landmark_tile_map";

        // Map of tilemap names to resource IDs
        Map<String, Integer> tileMapResources = new HashMap<>();

        {
            tileMapResources.put("easy_landmark_tile_map", R.drawable.easy_landmark_tile_map);
            tileMapResources.put("medium_landmark_tile_map", R.drawable.medium_landmark_tile_map);
            tileMapResources.put("hard_landmark_tile_map", R.drawable.hard_landmarks_tile_map);
        }

        Integer resourceId = tileMapResources.get(tileMapName);
        if (resourceId != null) {
            return resourceId;
        } else {
            Log.w("ResourceUtil", "No resource found for key: " + tileMapName);
            return -1;
        }
    }

    public static Bitmap getLandmarkImage(Bitmap tileMap, int row, int column) {
        int flagWidth = 400;
        int flagHeight = 300;

        // Calculate the starting position of the flag
        float scaleX = (float) tileMap.getWidth() / 1600; // 1600 is the original width
        float scaleY = (float) tileMap.getHeight() / 1500; // 1500 is the original height

        int startX = Math.round(column * flagWidth * scaleX);
        int startY = Math.round(row * flagHeight * scaleY);

        Log.d("FlagExtractor", "Tilemap dimensions: " + tileMap.getWidth() + "x" + tileMap.getHeight());
        Log.d("FlagExtractor", "Original dimensions: 1600x1500");
        Log.d("FlagExtractor", "Scale factors: X=" + scaleX + ", Y=" + scaleY);
        Log.d("FlagExtractor", "Extracting flag at: (" + startX + ", " + startY + ")");

        // Check if the requested position is within the tilemap bounds
        if (startX + Math.round(flagWidth * scaleX) > tileMap.getWidth() ||
                startY + Math.round(flagHeight * scaleY) > tileMap.getHeight()) {
            Log.w("FlagExtractor", "Flag out of bounds: (" + startX + ", " + startY + ")");
            return null;
        }

        // Extract the flag image
        return Bitmap.createBitmap(tileMap, startX, startY,
                Math.round(flagWidth * scaleX),
                Math.round(flagHeight * scaleY));
    }

    public static int getFlagResourceId(String ignoredKey, String difficultyLevel) {
        String tileMapName = difficultyLevel + "_flag_tile_map";

        // Map of tilemap names to resource IDs
        Map<String, Integer> tileMapResources = new HashMap<>();

        {
            tileMapResources.put("easy_flag_tile_map", R.drawable.easy_flag_tile_map);
            tileMapResources.put("medium_flag_tile_map", R.drawable.medium_flag_tile_map);
            tileMapResources.put("hard_flag_tile_map", R.drawable.hard_flag_tile_map);
            tileMapResources.put("very_hard_flag_tile_map", R.drawable.very_hard_flag_tile_map);
            tileMapResources.put("impossible_flag_tile_map", R.drawable.impossible_flag_tile_map);
        }

        Integer resourceId = tileMapResources.get(tileMapName);
        if (resourceId != null) {
            return resourceId;
        } else {
            Log.w("ResourceUtil", "No resource found for key: " + tileMapName);
            return -1;
        }
    }

    public static Bitmap getFlagImage(Bitmap tileMap, int row, int column) {
        int flagWidth = 300;
        int flagHeight = 200;

        // Calculate the starting position of the flag
        float scaleX = (float) tileMap.getWidth() / 2400; // 2400 is the original width
        float scaleY = (float) tileMap.getHeight() / 1000; // 1000 is the original height

        int startX = Math.round(column * flagWidth * scaleX);
        int startY = Math.round(row * flagHeight * scaleY);

        Log.d("FlagExtractor", "Tilemap dimensions: " + tileMap.getWidth() + "x" + tileMap.getHeight());
        Log.d("FlagExtractor", "Original dimensions: 2400x1000");
        Log.d("FlagExtractor", "Scale factors: X=" + scaleX + ", Y=" + scaleY);
        Log.d("FlagExtractor", "Extracting flag at: (" + startX + ", " + startY + ")");

        // Check if the requested position is within the tilemap bounds
        if (startX + Math.round(flagWidth * scaleX) > tileMap.getWidth() ||
                startY + Math.round(flagHeight * scaleY) > tileMap.getHeight()) {
            Log.w("FlagExtractor", "Flag out of bounds: (" + startX + ", " + startY + ")");
            return null;
        }

        // Extract the flag image
        return Bitmap.createBitmap(tileMap, startX, startY,
                Math.round(flagWidth * scaleX),
                Math.round(flagHeight * scaleY));
    }

    public static int getSportsResourceId (String ignoredKey, String difficultyLevel){
        String tileMapName = difficultyLevel + "_sport_tile_map";

        // Map of tilemap names to resource IDs
        Map<String, Integer> tileMapResources = new HashMap<>();

        {
            tileMapResources.put("easy_sport_tile_map", R.drawable.easy_sport_tile_map);
            tileMapResources.put("medium_sport_tile_map", R.drawable.medium_sport_tile_map);
            tileMapResources.put("hard_sport_tile_map", R.drawable.hard_sport_tile_map);
        }

        Integer resourceId = tileMapResources.get(tileMapName);
        if (resourceId != null) {
            return resourceId;
        } else {
            Log.w("ResourceUtil", "No resource found for key: " + tileMapName);
            return -1;
        }
    }

    public static Bitmap getSportsImage(Bitmap tileMap, int row, int column) {
        int imageWidth = 300;
        int imageHeight = 300;

        // Calculate the starting position of the flag
        float scaleX = (float) tileMap.getWidth() / 1200; // 1200 is the original width
        float scaleY = (float) tileMap.getHeight() / 1500; // 1500 is the original height

        int startX = Math.round(column * imageWidth * scaleX);
        int startY = Math.round(row * imageHeight * scaleY);

        Log.d("FlagExtractor", "Tilemap dimensions: " + tileMap.getWidth() + "x" + tileMap.getHeight());
        Log.d("FlagExtractor", "Original dimensions: 1600x1500");
        Log.d("FlagExtractor", "Scale factors: X=" + scaleX + ", Y=" + scaleY);
        Log.d("FlagExtractor", "Extracting flag at: (" + startX + ", " + startY + ")");

        // Check if the requested position is within the tilemap bounds
        if (startX + Math.round(imageWidth * scaleX) > tileMap.getWidth() ||
                startY + Math.round(imageHeight * scaleY) > tileMap.getHeight()) {
            Log.w("FlagExtractor", "Flag out of bounds: (" + startX + ", " + startY + ")");
            return null;
        }

        // Extract the flag image
        return Bitmap.createBitmap(tileMap, startX, startY,
                Math.round(imageWidth * scaleX),
                Math.round(imageHeight * scaleY));
    }

    public static int getBrandResourceId (String ignoredKey, String difficultyLevel){

        String tileMapName = difficultyLevel + "_brand_tile_map";

        // Map of tilemap names to resource IDs
        Map<String, Integer> tileMapResources = new HashMap<>();

        {
            tileMapResources.put("easy_brand_tile_map", R.drawable.easy_brand_tile_map);
            tileMapResources.put("medium_brand_tile_map", R.drawable.medium_brand_tile_map);
            tileMapResources.put("hard_brand_tile_map", R.drawable.hard_brand_tile_map);
        }

        Integer resourceId = tileMapResources.get(tileMapName);
        if (resourceId != null) {
            return resourceId;
        } else {
            Log.w("ResourceUtil", "No resource found for key: " + tileMapName);
            return -1;
        }
    }

    public static Bitmap getBrandImage(Bitmap tileMap, int row, int column) {
        int flagWidth = 400;
        int flagHeight = 300;

        // Calculate the starting position of the flag
        float scaleX = (float) tileMap.getWidth() / 1600; // 1600 is the original width
        float scaleY = (float) tileMap.getHeight() / 1500; // 1500 is the original height

        int startX = Math.round(column * flagWidth * scaleX);
        int startY = Math.round(row * flagHeight * scaleY);

        Log.d("FlagExtractor", "Tilemap dimensions: " + tileMap.getWidth() + "x" + tileMap.getHeight());
        Log.d("FlagExtractor", "Original dimensions: 1600x1500");
        Log.d("FlagExtractor", "Scale factors: X=" + scaleX + ", Y=" + scaleY);
        Log.d("FlagExtractor", "Extracting flag at: (" + startX + ", " + startY + ")");

        // Check if the requested position is within the tilemap bounds
        if (startX + Math.round(flagWidth * scaleX) > tileMap.getWidth() ||
                startY + Math.round(flagHeight * scaleY) > tileMap.getHeight()) {
            Log.w("FlagExtractor", "Flag out of bounds: (" + startX + ", " + startY + ")");
            return null;
        }

        // Extract the flag image
        return Bitmap.createBitmap(tileMap, startX, startY,
                Math.round(flagWidth * scaleX),
                Math.round(flagHeight * scaleY));
    }

    public static int getFoodResourceId (String ignoredKey, String difficultyLevel){

        String tileMapName = difficultyLevel + "_food_tile_map";

        // Map of tilemap names to resource IDs
        Map<String, Integer> tileMapResources = new HashMap<>();

        {
            tileMapResources.put("easy_food_tile_map", R.drawable.easy_food_tile_map);
            tileMapResources.put("medium_food_tile_map", R.drawable.medium_food_tile_map);
            tileMapResources.put("hard_food_tile_map", R.drawable.hard_food_tile_map);
        }

        Integer resourceId = tileMapResources.get(tileMapName);
        if (resourceId != null) {
            return resourceId;
        } else {
            Log.w("ResourceUtil", "No resource found for key: " + tileMapName);
            return -1;
        }
    }

    public static Bitmap getFoodImage(Bitmap tileMap, int row, int column) {
        int flagWidth = 400;
        int flagHeight = 300;

        // Calculate the starting position of the flag
        float scaleX = (float) tileMap.getWidth() / 1600; // 1600 is the original width
        float scaleY = (float) tileMap.getHeight() / 1500; // 1500 is the original height

        int startX = Math.round(column * flagWidth * scaleX);
        int startY = Math.round(row * flagHeight * scaleY);

        Log.d("FlagExtractor", "Tilemap dimensions: " + tileMap.getWidth() + "x" + tileMap.getHeight());
        Log.d("FlagExtractor", "Original dimensions: 1600x1500");
        Log.d("FlagExtractor", "Scale factors: X=" + scaleX + ", Y=" + scaleY);
        Log.d("FlagExtractor", "Extracting flag at: (" + startX + ", " + startY + ")");

        // Check if the requested position is within the tilemap bounds
        if (startX + Math.round(flagWidth * scaleX) > tileMap.getWidth() ||
                startY + Math.round(flagHeight * scaleY) > tileMap.getHeight()) {
            Log.w("FlagExtractor", "Flag out of bounds: (" + startX + ", " + startY + ")");
            return null;
        }

        // Extract the flag image
        return Bitmap.createBitmap(tileMap, startX, startY,
                Math.round(flagWidth * scaleX),
                Math.round(flagHeight * scaleY));
    }
}

