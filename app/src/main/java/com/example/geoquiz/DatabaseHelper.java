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

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 2;
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
        String CREATE_COUNTRIES_TABLE = "CREATE TABLE IF NOT EXISTS countries (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "country_name TEXT NOT NULL, " +
                "country_capital TEXT NOT NULL, " +
                "flag_path TEXT NOT NULL, " +
                "capital_difficulty INTEGER, " +
                "flag_difficulty INTEGER, " +
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS countries");
        db.execSQL("DROP TABLE IF EXISTS difficulty");
        db.execSQL("DROP TABLE IF EXISTS landmarks");

        // Create tables again
        onCreate(db);
    }

    private boolean checkDatabase() {
        try {
            final String mPath = DB_PATH + DB_NAME;
            final File file = new File(mPath);
            if (file.exists())
                return true;
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
            // Rethrow the exception to allow further handling
            throw new IOException("Failed to copy database", e);
        }
    }

    public void createDatabase() throws IOException {

        boolean mDatabaseExists = checkDatabase();
        if (!mDatabaseExists) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDatabase();
            } catch (IOException mIOException) {
                mIOException.printStackTrace();
                throw new Error("Error copying Database");
            } finally {
                this.close();
            }
        }
    }

    @Override
    public synchronized void close() {
        if(myDatabase != null){
            myDatabase.close();
            SQLiteDatabase.releaseMemory();
            super.close();
        }
    }

    public String loadHandler() {
        try {
            createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (tableExists("countries")) {
            String result = "";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM Landmarks", null
            );

            while (c.moveToNext()) {
                int result_id = c.getInt(0);
                String result_name = c.getString(1);
                result += String.valueOf(result_id) + "" + result_name + System.getProperty("line.separator");
            }

            c.close();
            db.close();
            return result;
        } else {
            // Handle the case where the table does not exist
            return "Table 'countries' does not exist.";
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

}
