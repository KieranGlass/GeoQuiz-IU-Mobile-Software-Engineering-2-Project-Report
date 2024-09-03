package com.example.geoquiz;public class Food {

    private int id;
    private String name;
    private int country_id;
    private int difficulty_id;
    private int tilemap_row;
    private int tilemap_column;
    private String food_path;

    public Food(int id, String name, int country_id, int difficulty_id, int tilemap_row, int tilemap_column, String imagePath) {
        this.id = id;
        this.name = name;
        this.country_id = country_id;
        this.food_path = imagePath;
        this.difficulty_id = difficulty_id;
        this.tilemap_row = tilemap_row;
        this.tilemap_column = tilemap_column;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getCountry_id() { return country_id; }
    public String getFood_path() { return food_path; }
    public int getDifficulty_id() { return difficulty_id; }
    public int getTilemap_row() { return tilemap_row; }
    public int getTilemap_column() { return tilemap_column; }

}

