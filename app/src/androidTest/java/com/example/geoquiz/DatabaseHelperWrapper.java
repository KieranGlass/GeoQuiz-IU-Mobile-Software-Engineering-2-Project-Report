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

}

