package com.example.geoquiz;

import android.app.Application;

public class myApp extends Application {

    // TODO - TESTING FOR EVERY QUIZ TYPE + DATABASE ACTIONS
    // TODO - ADD CURRENT BEST SCORE FOR QUIZ TYPE ON RESULTS PAGE
    // TODO - MAKE SIGNING UP A POP-UP RATHER THAN PERMANENT FEATURE ON PAGE

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseHelper dbHelper = new DatabaseHelper(myApp.this, null, null, DatabaseHelper.DB_VERSION);
        dbHelper.loadHandler();
    }
}
