package com.example.geoquiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class QuizDashboard extends AppCompatActivity {

    Button flagBtn, capitalBtn, landmarkBtn, foodBtn, sportsBtn, brandBtn;

    TextView tvUsername, tvFlagCompletion, tvCapitalCompletion, tvLandmarkCompletion, tvFoodCompletion, tvSportsCompletion, tvBrandCompletion;
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        User currentUser = UserLogin.getCurrentUser();

        if(currentUser == null) {
            Intent intent = new Intent(QuizDashboard.this, MainActivity.class);
        }

        username = currentUser.getUsername();


        tvUsername = findViewById(R.id.tvUsername);
        tvFlagCompletion = findViewById(R.id.tvFlagCompletion);
        tvCapitalCompletion = findViewById(R.id.tvCapitalCompletion);
        tvLandmarkCompletion = findViewById(R.id.tvLandmarkCompletion);
        tvFoodCompletion = findViewById(R.id.tvFoodCompletion);
        tvSportsCompletion = findViewById(R.id.tvSportsCompletion);
        tvBrandCompletion = findViewById(R.id.tvBrandCompletion);

        tvUsername.setText(username);

        displayCompletions();

        flagBtn = findViewById(R.id.flagBtn);
        capitalBtn = findViewById(R.id.capitalBtn);
        landmarkBtn = findViewById(R.id.landmarkBtn);
        foodBtn = findViewById(R.id.foodBtn);
        sportsBtn = findViewById(R.id.sportsBtn);
        brandBtn = findViewById(R.id.brandBtn);

        flagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (QuizDashboard.this, FlagCapitalDifficulty.class);
                intent.putExtra("Category", "Flag");
                startActivity(intent);
            }
        });

        capitalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (QuizDashboard.this, FlagCapitalDifficulty.class);
                intent.putExtra("Category", "Capital");
                startActivity(intent);
            }
        });

        landmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (QuizDashboard.this, Difficulty.class);
                intent.putExtra("Category", "Landmark");
                startActivity(intent);
            }
        });

        foodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (QuizDashboard.this, Difficulty.class);
                intent.putExtra("Category", "Food");
                startActivity(intent);
            }
        });

        sportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (QuizDashboard.this, Difficulty.class);
                intent.putExtra("Category", "Sports");
                startActivity(intent);
            }
        });

        brandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (QuizDashboard.this, Difficulty.class);
                intent.putExtra("Category", "Brand");
                startActivity(intent);
            }
        });
    }


    public void displayCompletions(){

        DatabaseHelper flagHelper = new DatabaseHelper(QuizDashboard.this, null, null, DatabaseHelper.DB_VERSION);
        DatabaseHelper capitalHelper = new DatabaseHelper(QuizDashboard.this, null, null, DatabaseHelper.DB_VERSION);
        DatabaseHelper landmarkHelper = new DatabaseHelper(QuizDashboard.this, null, null, DatabaseHelper.DB_VERSION);
        DatabaseHelper foodHelper = new DatabaseHelper(QuizDashboard.this, null, null, DatabaseHelper.DB_VERSION);
        DatabaseHelper sportsHelper = new DatabaseHelper(QuizDashboard.this, null, null, DatabaseHelper.DB_VERSION);
        DatabaseHelper brandHelper = new DatabaseHelper(QuizDashboard.this, null, null, DatabaseHelper.DB_VERSION);

        UserProgress flagProgress = flagHelper.getUserProgressByCategory(UserLogin.getCurrentUser().getId(), 6);
        UserProgress capitalProgress = capitalHelper.getUserProgressByCategory(UserLogin.getCurrentUser().getId(), 5);
        UserProgress landmarkProgress = landmarkHelper.getUserProgressByCategory(UserLogin.getCurrentUser().getId(), 4);
        UserProgress foodProgress = foodHelper.getUserProgressByCategory(UserLogin.getCurrentUser().getId(), 3);
        UserProgress sportsProgress = sportsHelper.getUserProgressByCategory(UserLogin.getCurrentUser().getId(), 2);
        UserProgress brandProgress = brandHelper.getUserProgressByCategory(UserLogin.getCurrentUser().getId(), 1);


        displayCompletion(tvFlagCompletion, flagProgress, 5);
        displayCompletion(tvCapitalCompletion, capitalProgress, 5);
        displayCompletion(tvLandmarkCompletion, landmarkProgress, 3);
        displayCompletion(tvFoodCompletion, foodProgress, 3);
        displayCompletion(tvSportsCompletion, sportsProgress, 3);
        displayCompletion(tvBrandCompletion, brandProgress,3);

    }

    private void displayCompletion(TextView textView, UserProgress progress, int divider) {
        String percent = "%";
        if (progress == null || progress.getBestScore() <= 0) {
            textView.setText("0");
        } else {
            textView.setText(String.valueOf((progress.getBestScore() * 5 / divider)) + percent);
        }
    }
}
