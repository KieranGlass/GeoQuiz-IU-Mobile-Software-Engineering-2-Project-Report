package com.example.geoquiz;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.database.SQLException;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 6;
    private static final String DB_NAME = "GeoQuizDatabase.db";
    private String DB_PATH = "/data/data/com.example.geoquiz/databases/";
    SQLiteDatabase myDatabase;
    private final Context mContext;

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Creating 'countries' table
        String CREATE_COUNTRIES_TABLE = "CREATE TABLE IF NOT EXISTS countries (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "country_name TEXT NOT NULL, " +
                "country_capital TEXT NOT NULL, " +
                "flag_path TEXT NOT NULL, " +
                "capital_difficulty INTEGER NOT NULL, " +
                "flag_difficulty INTEGER NOT NULL, " +
                "continent_id INTEGER NOT NULL, " +
                "tilemap_row INTEGER NOT NULL, " +
                "tilemap_column INTEGER NOT NULL, " +
                "FOREIGN KEY(continent_id) REFERENCES continents(id), " +
                "FOREIGN KEY(capital_difficulty) REFERENCES difficulty(id), " +
                "FOREIGN KEY(flag_difficulty) REFERENCES difficulty(id))";
        db.execSQL(CREATE_COUNTRIES_TABLE);

        // Creating the 'difficulty' table
        String CREATE_DIFFICULTY_TABLE = "CREATE TABLE IF NOT EXISTS difficulty (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "difficulty_type TEXT NOT NULL)";
        db.execSQL(CREATE_DIFFICULTY_TABLE);

        // Creating the 'landmarks' table
        String CREATE_LANDMARKS_TABLE = "CREATE TABLE IF NOT EXISTS landmarks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "landmark_name TEXT NOT NULL, " +
                "country_id INTEGER, " +
                "landmark_path TEXT, " +
                "difficulty_id INTEGER, " +
                "FOREIGN KEY(country_id) REFERENCES countries(id), " +
                "FOREIGN KEY(difficulty_id) REFERENCES difficulty(id))";
        db.execSQL(CREATE_LANDMARKS_TABLE);

        // Creating the 'continents' table
        String CREATE_CONTINENT_TABLE = "CREATE TABLE IF NOT EXISTS continents (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "continent_name TEXT NOT NULL)";
        db.execSQL(CREATE_CONTINENT_TABLE);

        // Creating the 'food' table
        String CREATE_FOOD_TABLE = "CREATE TABLE IF NOT EXISTS food (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "food_name TEXT NOT NULL, " +
                "country_id INTEGER NOT NULL, " +
                "difficulty_id INTEGER NOT NULL, " +
                "tilemap_row INTEGER NOT NULL, " +
                "tilmap_column INTEGER NOT NULL, " +
                "food_path TEXT, " +
                "FOREIGN KEY(country_id) REFERENCES countries(id), " +
                "FOREIGN KEY(difficulty_id) REFERENCES difficulty(id))";
        db.execSQL(CREATE_FOOD_TABLE);

        // Creating the 'sports' table
        String CREATE_SPORTS_TABLE = "CREATE TABLE IF NOT EXISTS sports (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "team_name TEXT NOT NULL, " +
                "country_id INTEGER NOT NULL, " +
                "difficulty_id INTEGER NOT NULL, " +
                "sport_path TEXT, " +
                "tilemap_row INTEGER NOT NULL, " +
                "tilmap_column INTEGER NOT NULL, " +
                "FOREIGN KEY(country_id) REFERENCES countries(id), " +
                "FOREIGN KEY(difficulty_id) REFERENCES difficulty(id))";
        db.execSQL(CREATE_SPORTS_TABLE);

        // Creating the 'brands' table
        String CREATE_BRANDS_TABLE = "CREATE TABLE IF NOT EXISTS brands (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "brand_name TEXT NOT NULL, " +
                "country_id INTEGER NOT NULL, " +
                "difficulty_id INTEGER NOT NULL, " +
                "brand_path TEXT, " +
                "tilemap_row INTEGER NOT NULL, " +
                "tilmap_column INTEGER NOT NULL, " +
                "FOREIGN KEY(country_id) REFERENCES countries(id), " +
                "FOREIGN KEY(difficulty_id) REFERENCES difficulty(id))";
        db.execSQL(CREATE_BRANDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS countries");
        db.execSQL("DROP TABLE IF EXISTS difficulty");
        db.execSQL("DROP TABLE IF EXISTS Landmarks");
        db.execSQL("DROP TABLE IF EXISTS continents");
        db.execSQL("DROP TABLE IF EXISTS food");
        db.execSQL("DROP TABLE IF EXISTS sports");
        db.execSQL("DROP TABLE IF EXISTS brands");

        // Create tables again
        onCreate(db);
    }

    private boolean checkDatabase() {
        try {
            final String mPath = DB_PATH + DB_NAME;
            final File file = new File(mPath);
            if (file.exists()) {
                return true;
            }
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void copyDatabase() throws IOException {
        try {
            InputStream mInputStream = mContext.getAssets().open(DB_NAME);
            String outFilename = DB_PATH + DB_NAME;
            OutputStream mOutputStream = new FileOutputStream(outFilename);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = mInputStream.read(buffer)) > 0) {
                mOutputStream.write(buffer, 0, length);
            }
            mOutputStream.flush();
            mOutputStream.close();
            mInputStream.close();
            Log.d("DatabaseHelper", "Database copied successfully.");
        } catch (IOException e) {
            Log.e("DatabaseHelper", "Failed to copy database", e);
        }
    }

    public void open() {

        myDatabase = this.getReadableDatabase();
    }
    @Override
    public synchronized void close() {
        if(myDatabase != null){
            myDatabase.close();
            SQLiteDatabase.releaseMemory();
            super.close();
        }
    }

    public void loadHandler() {
        try {
            boolean mDatabaseExists = checkDatabase();
            if (!mDatabaseExists) {
                Log.i("DatabaseHelper", "Copying database...");
                copyDatabase();
                Log.i("DatabaseHelper", "Database copied successfully.");
            }
            copyDatabase();
            myDatabase = this.getReadableDatabase();
            Log.i("DatabaseHelper", "Database opened successfully.");

            // Example of executing a query and logging results
            Cursor cursor = myDatabase.rawQuery("SELECT COUNT(*) FROM your_table_name", null);
            if (cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                Log.d("DatabaseHelper", "Query result: " + count);
            } else {
                Log.w("DatabaseHelper", "Cursor is empty");
            }
            cursor.close();
        } catch (IOException | SQLException e) {
            Log.e("DatabaseHelper", "Error loading database", e);
        }
    }

    private boolean tableExists(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='" + tableName + "'", null);
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return count > 0;
        }
        cursor.close();
        return false;
    }


    // ALL SQL RETRIEVAL METHODS BELOW //

    public List<Landmark> fetchLandmarks() {

        List<Landmark> landmarks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Landmarks", null);

        if (cursor.moveToFirst()) {
            do {
                landmarks.add(new Landmark(
                        cursor.getInt(0), // ID
                        cursor.getString(1), // landmark name
                        cursor.getInt(2),   // county id
                        cursor.getString(3), // image path
                        cursor.getInt(4), // difficulty id
                        cursor.getInt(5), // tilemap row
                        cursor.getInt(6) // tilemap column

                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return landmarks;
    }

    public List<Country> fetchCountries() {

        List<Country> countries = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM countries", null);

        if (cursor.moveToFirst()) {
            do {
                countries.add(new Country(
                        cursor.getInt(0), // country id
                        cursor.getString(1), // country name
                        cursor.getString(2), // country capital
                        cursor.getString(3), // country flag image path
                        cursor.getInt(4), // capital difficulty id
                        cursor.getInt(5), // flag difficulty id
                        cursor.getInt(6), // continent id
                        cursor.getInt(7), // tilemap row
                        cursor.getInt(8)  // tilemap column
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return countries;
    }

    public List<Sports> fetchSports() {

        List<Sports> sports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM sports", null);

        if (cursor.moveToFirst()) {
            do {
                sports.add(new Sports(
                        cursor.getInt(0), // ID
                        cursor.getString(1), // team name
                        cursor.getInt(2),   // county id
                        cursor.getInt(3), // difficulty id
                        cursor.getString(4), // sport image path
                        cursor.getInt(5), // tilemap row
                        cursor.getInt(6) // tilemap column

                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return sports;
    }

}
