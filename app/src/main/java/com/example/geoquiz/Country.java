package com.example.geoquiz;

public class Country {

    private int id;
    private String country_name;
    private String country_capital;
    private String flag_path;
    private int capital_difficulty;
    private int flag_difficulty;

    private int continent_id;
    private int tilemap_row;
    private int tilemap_column;


    public Country(int id, String country_name, String country_capital,
                   String flag_path, int capital_difficulty, int flag_difficulty,
                   int continent_id, int tilemap_row, int tilemap_column) {

        this.id = id;
        this.country_name = country_name;
        this.country_capital = country_capital;
        this.flag_path = flag_path;
        this.capital_difficulty = capital_difficulty;
        this.flag_difficulty = flag_difficulty;
        this.continent_id = continent_id;
        this.tilemap_row = tilemap_row;
        this.tilemap_column = tilemap_column;
    }


    // Getters and setters

    public int getId() { return id; }
    public String getCountry_name() { return country_name; }
    public String getCountry_capital() { return country_capital; }
    public String getFlag_path() { return flag_path; }
    public int getCapital_difficulty() { return capital_difficulty; }
    public int getFlag_difficulty() { return flag_difficulty; }
    public int getContinent_id() { return continent_id; }
    public int getTilemap_row() { return tilemap_row; }
    public int getTilemap_column() { return tilemap_column; }
}
