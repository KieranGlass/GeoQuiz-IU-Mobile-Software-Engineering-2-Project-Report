package com.example.geoquiz;

import java.util.List;

public class DatabaseHelperWrapper {
    private final DatabaseHelper dbHelper;

    public DatabaseHelperWrapper(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    public List<Country> fetchCountries() {
        return dbHelper.fetchCountries();
    }
    public List<Food> fetchFoods() {
        return dbHelper.fetchFoods();
    }
    public List<Sports> fetchSports() {
        return dbHelper.fetchSports();
    }
    public List<Landmark> fetchLandmarks() {
        return dbHelper.fetchLandmarks();
    }
    public List<Brand> fetchBrands() {
        return dbHelper.fetchBrands();
    }
    public List<UserProgress> getUserProgressByCategory(int userId, int categoryId) {return dbHelper.getUserProgressByCategory(userId, categoryId);
    }


}

