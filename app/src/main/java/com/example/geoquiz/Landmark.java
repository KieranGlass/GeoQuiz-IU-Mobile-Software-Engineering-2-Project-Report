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
    private int tilemap_row;
    private int tilemap_column;

    public Landmark(int id, String name, int country_id, String imagePath, int difficulty_id, int tilemap_row, int tilemap_column) {
        this.id = id;
        this.name = name;
        this.country_id = country_id;
        this.imagePath = imagePath;
        this.difficulty_id = difficulty_id;
        this.tilemap_row = tilemap_row;
        this.tilemap_column = tilemap_column;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getCountry_id() { return country_id; }
    public String getImagePath() { return imagePath; }
    public int getDifficulty_id() { return difficulty_id; }
    public int getTilemap_row() { return tilemap_row; }
    public int getTilemap_column() { return tilemap_column; }

}
