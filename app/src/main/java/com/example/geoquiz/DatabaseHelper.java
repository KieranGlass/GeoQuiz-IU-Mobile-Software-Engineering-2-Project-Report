package com.example.geoquiz;

import java.io.FileOutputStream;
import java.io.IOException;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.SQLException;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 8;
    private static final String DB_NAME = "GeoQuizDatabase.db";
    private final String DB_PATH = "/data/data/com.example.geoquiz/databases/";
    SQLiteDatabase myDatabase;
    private final Context mContext;

    public DatabaseHelper(@Nullable Context context, @Nullable String ignoredName, @Nullable SQLiteDatabase.CursorFactory factory, int ignoredVersion) {
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
                "tilemap_column INTEGER NOT NULL, " +
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

        // Creating the Users Table
        String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL, " +
                "password TEXT NOT NULL)";
        db.execSQL(CREATE_USERS_TABLE);

        // Creating Categories Table
        String CREATE_CATEGORIES_TABLE = "CREATE TABLE IF NOT EXISTS categories (" +
                "category_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "category_name TEXT NOT NULL)";
        db.execSQL(CREATE_CATEGORIES_TABLE);

        // Creating user progress table
        String CREATE_USER_PROGRESS_TABLE = "CREATE TABLE IF NOT EXISTS user_progress (" +
                "user_id INTEGER, " +
                "category_id INTEGER, " +
                "difficulty_id INTEGER, " +
                "best_score REAL, " +
                "FOREIGN KEY(user_id) REFERENCES users(user_id), " +
                "FOREIGN KEY(category_id) REFERENCES categories(category_id), " +
                "FOREIGN KEY(difficulty_id) REFERENCES difficulty(id), " +
                "PRIMARY KEY(user_id, category_id, difficulty_id))";
        db.execSQL(CREATE_USER_PROGRESS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS countries");
        db.execSQL("DROP TABLE IF EXISTS difficulty");
        db.execSQL("DROP TABLE IF EXISTS Landmarks");
        db.execSQL("DROP TABLE IF EXISTS continents");
        db.execSQL("DROP TABLE IF EXISTS food");
        db.execSQL("DROP TABLE IF EXISTS sports");
        db.execSQL("DROP TABLE IF EXISTS brands");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS categories");
        db.execSQL("DROP TABLE IF EXISTS user_progress");

        // Create tables again
        onCreate(db);

    }

    private boolean checkDatabase() {
        try {
            assert mContext != null;
            return mContext.getDatabasePath(DB_NAME).exists();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void copyDatabase() throws IOException {
        try {
            assert mContext != null;
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
            Log.d("DatabaseHelper", "Database copied successfully1.");
        } catch (IOException e) {
            Log.e("DatabaseHelper", "Failed to copy database", e);
        }
    }

    @Override
    public synchronized void close() {
        if (myDatabase != null) {
            myDatabase.close();
            SQLiteDatabase.releaseMemory();
            super.close();
        }
    }

    // Load Handler method called in application activity. Calls copyDatabase method if necesarry
    // database is up to date. Puts this into a SQLiteDatabase variable myDatabase
    public void loadHandler() {
        try {
            boolean mDatabaseExists = checkDatabase();
            if (!mDatabaseExists) {
                Log.i("DatabaseHelper", "Copying database...");
                copyDatabase();
                Log.i("DatabaseHelper", "Database copied successfully.");
            }
            //copyDatabase(); // uncommenting this allows me to quickly reset the devices internal database
            myDatabase = this.getReadableDatabase();
            Log.i("DatabaseHelper", "Database opened successfully.");


            Cursor cursor = myDatabase.rawQuery("SELECT COUNT(*) FROM users", null);
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

    // -- ALL SQL RETRIEVAL METHODS FOR QUIZ QUESTIONS BELOW -- //

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

    public List<Food> fetchFoods() {

        List<Food> foods = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM food", null);

        if (cursor.moveToFirst()) {
            do {
                foods.add(new Food(
                        cursor.getInt(0), // ID
                        cursor.getString(1), // food name
                        cursor.getInt(2),   // county id
                        cursor.getInt(3), // difficulty id
                        cursor.getInt(4), // tilemap row
                        cursor.getInt(5), // tilemap column
                        cursor.getString(6) // food image path

                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return foods;
    }

    public List<Brand> fetchBrands() {

        List<Brand> brands = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM brands", null);

        if (cursor.moveToFirst()) {
            do {
                brands.add(new Brand(
                        cursor.getInt(0), // ID
                        cursor.getString(1), // brand name
                        cursor.getInt(2),   // country id
                        cursor.getInt(3), // difficulty id
                        cursor.getString(4), // brand image path
                        cursor.getInt(5), // tilemap row
                        cursor.getInt(6) // tilemap column

                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return brands;
    }

    // -- ALL METHODS RELATED TO USERS AND LOGINS BELOW-- //

    public void createUser(String username, String password) {

        SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor = db.query("users", new String[]{"username"}, "username = ?", new String[]{username}, null, null, null);

            if (cursor.getCount() > 0) {
                cursor.close();
                db.close();
                Toast.makeText(this.mContext, "Username already exists!", Toast.LENGTH_SHORT).show();
                return;
            }

            cursor.close();
            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("password", password);

            long newRowId = db.insert("users", null, values);

            if (newRowId == -1) {
                Log.e("DatabaseHelper", "Failed to insert new user.");
                Toast.makeText(mContext, "Error inserting new user", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("DatabaseHelper", "New user inserted with ID: " + newRowId);
                Toast.makeText(mContext, "User created successfully!", Toast.LENGTH_SHORT).show();
            }

        db.close();
    }

    @SuppressLint("Range")
    public User getUserByCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"user_id", "username", "password"};
        String selection = "username" + " = ? AND " + "password" + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex("user_id")));
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            // Set other fields...

            cursor.close();
        }

        db.close();
        return user;
    }

    public void updateUserProgress(int userId, int categoryId, int difficultyId, int score) {
        SQLiteDatabase db = this.getWritableDatabase(); // Accesses a writeable database
        // Check if an entry already exists for that user in that category and didfficulty
        Cursor cursor = db.rawQuery("SELECT * FROM user_progress WHERE user_id = ? AND category_id = ? AND difficulty_id = ?",
                new String[]{String.valueOf(userId), String.valueOf(categoryId), String.valueOf(difficultyId)});
        // Assign values to ContentValues
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", userId);
        contentValues.put("category_id", categoryId);
        contentValues.put("difficulty_id", difficultyId);
        contentValues.put("best_score", score);
        // null cursor check
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int existingScore = cursor.getInt(cursor.getColumnIndex("best_score")); // assign current score to 'existing score
            if (score > existingScore) {  // check if new score in an imporvement, if so updat database entry
                db.update("user_progress", contentValues, "user_id" + " = ? AND " + "category_id" + " = ? AND " + "difficulty_id" + " = ?",
                        new String[]{String.valueOf(userId), String.valueOf(categoryId), String.valueOf(difficultyId)});
            }
        } else { // if null, insert first entry into db
            db.insert("user_progress", null, contentValues);
        }
        if (cursor != null) {
            cursor.close(); // close cursor and db
        }
        db.close();
    }

    @SuppressLint("Range")
    public List<UserProgress> getUserProgressByCategory(int userId, int categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<UserProgress> progressList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM user_progress WHERE user_id = ? AND category_id = ?", new String[]{String.valueOf(userId), String.valueOf(categoryId)});

        //TODO MAKE THIS INTO A LIST TO GET EACH DIFFICULTY SCORE!
        if (cursor.moveToFirst()) {
            do {
                UserProgress progress = new UserProgress();
                progress.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
                progress.setCategoryId(cursor.getInt(cursor.getColumnIndex("category_id")));
                progress.setDifficultyId(cursor.getInt(cursor.getColumnIndex("difficulty_id")));
                progress.setBestScore(cursor.getInt(cursor.getColumnIndex("best_score")));
                progressList.add(progress);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return progressList;
    }
}
