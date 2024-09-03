package com.example.geoquiz;public class Sports {
    private int id;
    private String name;
    private int country_id;
    private int difficulty_id;
    private String sport_path;
    private int tilemap_row;
    private int tilemap_column;

    public Sports(int id, String name, int country_id, int difficulty_id, String sport_path, int tilemap_row, int tilemap_column) {
        this.id = id;
        this.name = name;
        this.country_id = country_id;
        this.sport_path = sport_path;
        this.difficulty_id = difficulty_id;
        this.tilemap_row = tilemap_row;
        this.tilemap_column = tilemap_column;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getCountry_id() { return country_id; }
    public String getSport_path() { return sport_path; }
    public int getDifficulty_id() { return difficulty_id; }
    public int getTilemap_row() { return tilemap_row; }
    public int getTilemap_column() { return tilemap_column; }

}