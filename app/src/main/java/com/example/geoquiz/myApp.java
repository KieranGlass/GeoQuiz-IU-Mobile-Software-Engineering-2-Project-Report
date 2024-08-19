package com.example.geoquiz;

import android.app.Application;

public class myApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseHelper dbHelper = new DatabaseHelper(myApp.this, null, null, DatabaseHelper.DB_VERSION);
        dbHelper.loadHandler();
    }
}
