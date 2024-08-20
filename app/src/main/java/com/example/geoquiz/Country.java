package com.example.geoquiz;

public class Country {

    private int id;
    private String country_name;
    private String country_capital;
    private String flag_path;
    private int capital_difficulty;
    private int flag_difficulty;

    //TODO - Write similar classes for capital object and three new quiz catgeories.


    public Country(int id, String country_name, String country_capital,
                   String flag_path, int capital_difficulty, int flag_difficulty) {

        this.id = id;
        this.country_name = country_name;
        this.country_capital = country_capital;
        this.flag_path = flag_path;
        this.capital_difficulty = capital_difficulty;
        this.flag_difficulty = flag_difficulty;
    }


    // Getters and setters

    public int getId() { return id; }
    public String getCountry_name() { return country_name; }
    public String getCountry_capital() { return country_capital; }
    public String getFlag_path() { return flag_path; }
    public int getCapital_difficulty() { return capital_difficulty; }
    public int getFlag_difficulty() { return flag_difficulty; }
}
