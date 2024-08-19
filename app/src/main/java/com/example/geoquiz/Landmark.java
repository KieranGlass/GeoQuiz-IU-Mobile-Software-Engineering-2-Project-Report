package com.example.geoquiz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Landmark {

    private int id;
    private String name;
    private int country_id;
    private String imagePath;
    private int difficulty_id;

    public Landmark(int id, String name, int country_id, String imagePath, int difficulty_id) {
        this.id = id;
        this.name = name;
        this.country_id = country_id;
        this.imagePath = imagePath;
        this.difficulty_id = difficulty_id;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getCountry_id() { return country_id; }
    public String getImagePath() { return imagePath; }
    public int getDifficulty_id() { return difficulty_id; }


    public static Landmark getRandomLandmark(List<Landmark> landmarks) {
        Random random = new Random();
        int randomIndex = random.nextInt(landmarks.size());
        Landmark randomLandmark = landmarks.get(randomIndex);
        landmarks.remove(randomIndex); // Remove the selected landmark to avoid repetition
        return randomLandmark;
    }
}
