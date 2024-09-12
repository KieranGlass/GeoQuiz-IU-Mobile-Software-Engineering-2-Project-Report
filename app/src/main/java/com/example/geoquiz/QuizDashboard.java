package com.example.geoquiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.List;

/** @noinspection resource*/
public class QuizDashboard extends AppCompatActivity {

    Button flagBtn, capitalBtn, landmarkBtn, foodBtn, sportsBtn, brandBtn;
    TextView tvUsername, tvFlagCompletion, tvCapitalCompletion, tvLandmarkCompletion, tvFoodCompletion, tvSportsCompletion, tvBrandCompletion;
    String username = "";

    @SuppressLint("SetTextI18n")
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
            startActivity(intent);
        } else {
            username = currentUser.getUsername();
        }

        tvUsername = findViewById(R.id.tvUsername);
        tvFlagCompletion = findViewById(R.id.tvFlagCompletion);
        tvCapitalCompletion = findViewById(R.id.tvCapitalCompletion);
        tvLandmarkCompletion = findViewById(R.id.tvLandmarkCompletion);
        tvFoodCompletion = findViewById(R.id.tvFoodCompletion);
        tvSportsCompletion = findViewById(R.id.tvSportsCompletion);
        tvBrandCompletion = findViewById(R.id.tvBrandCompletion);

        tvUsername.setText("Welcome " + username);

        displayCompletions();

        flagBtn = findViewById(R.id.flagBtn);
        capitalBtn = findViewById(R.id.capitalBtn);
        landmarkBtn = findViewById(R.id.landmarkBtn);
        foodBtn = findViewById(R.id.foodBtn);
        sportsBtn = findViewById(R.id.sportsBtn);
        brandBtn = findViewById(R.id.brandBtn);

        flagBtn.setOnClickListener(view -> {
            Intent intent = new Intent (QuizDashboard.this, FlagCapitalDifficulty.class);
            intent.putExtra("Category", "Flag");
            startActivity(intent);
        });

        capitalBtn.setOnClickListener(view -> {
            Intent intent = new Intent (QuizDashboard.this, FlagCapitalDifficulty.class);
            intent.putExtra("Category", "Capital");
            startActivity(intent);
        });

        landmarkBtn.setOnClickListener(view -> {
            Intent intent = new Intent (QuizDashboard.this, Difficulty.class);
            intent.putExtra("Category", "Landmark");
            startActivity(intent);
        });

        foodBtn.setOnClickListener(view -> {
            Intent intent = new Intent (QuizDashboard.this, Difficulty.class);
            intent.putExtra("Category", "Food");
            startActivity(intent);
        });

        sportsBtn.setOnClickListener(view -> {
            Intent intent = new Intent (QuizDashboard.this, Difficulty.class);
            intent.putExtra("Category", "Sports");
            startActivity(intent);
        });

        brandBtn.setOnClickListener(view -> {
            Intent intent = new Intent (QuizDashboard.this, Difficulty.class);
            intent.putExtra("Category", "Brand");
            startActivity(intent);
        });
    }

    // Methods for displaying logged in users progress overall for a quiz category. I.e only full marks in all difficulties equals 100%
    public void displayCompletions(){

        DatabaseHelper flagHelper = new DatabaseHelper(QuizDashboard.this, null, null, DatabaseHelper.DB_VERSION);
        DatabaseHelper capitalHelper = new DatabaseHelper(QuizDashboard.this, null, null, DatabaseHelper.DB_VERSION);
        DatabaseHelper landmarkHelper = new DatabaseHelper(QuizDashboard.this, null, null, DatabaseHelper.DB_VERSION);
        DatabaseHelper foodHelper = new DatabaseHelper(QuizDashboard.this, null, null, DatabaseHelper.DB_VERSION);
        DatabaseHelper sportsHelper = new DatabaseHelper(QuizDashboard.this, null, null, DatabaseHelper.DB_VERSION);
        DatabaseHelper brandHelper = new DatabaseHelper(QuizDashboard.this, null, null, DatabaseHelper.DB_VERSION);

        List<UserProgress> flagProgress = flagHelper.getUserProgressByCategory(UserLogin.getCurrentUser().getId(), 6);
        List<UserProgress> capitalProgress = capitalHelper.getUserProgressByCategory(UserLogin.getCurrentUser().getId(), 5);
        List<UserProgress> landmarkProgress = landmarkHelper.getUserProgressByCategory(UserLogin.getCurrentUser().getId(), 4);
        List<UserProgress> foodProgress = foodHelper.getUserProgressByCategory(UserLogin.getCurrentUser().getId(), 3);
        List<UserProgress> sportsProgress = sportsHelper.getUserProgressByCategory(UserLogin.getCurrentUser().getId(), 2);
        List<UserProgress> brandProgress = brandHelper.getUserProgressByCategory(UserLogin.getCurrentUser().getId(), 1);

        displayCompletion(tvFlagCompletion, flagProgress, 5);
        displayCompletion(tvCapitalCompletion, capitalProgress, 5);
        displayCompletion(tvLandmarkCompletion, landmarkProgress, 3);
        displayCompletion(tvFoodCompletion, foodProgress, 3);
        displayCompletion(tvSportsCompletion, sportsProgress, 3);
        displayCompletion(tvBrandCompletion, brandProgress,3);

    }
    @SuppressLint("SetTextI18n")
    private void displayCompletion(TextView textView, List<UserProgress> progresses, int divider) {
        String percent = "%";

        int totalQuestions = 20 * divider; // Total questions across all difficulties
        int correctAnswers = 0;

        for (UserProgress progress : progresses) {
            correctAnswers += progress.getBestScore(); // Sum up all correct answers
        }

        int progressPercentage = (int) (((float) correctAnswers / totalQuestions) * 100);

        textView.setText((Math.max(progressPercentage, 0)) + percent);
    }
}
