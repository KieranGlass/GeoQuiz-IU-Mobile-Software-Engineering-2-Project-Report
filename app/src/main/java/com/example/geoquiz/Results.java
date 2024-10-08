package com.example.geoquiz;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Results extends AppCompatActivity {

    Button tryAgainBtn, homeBtn, dashboardBtn;
    TextView difficultyChosen, categoryChosen, scoreObtained, resultWord;
    ImageView ivResults;
    String difficultyReceived, categoryReceived;
    double score, scorePercentage;
    long finishedScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        tryAgainBtn = findViewById(R.id.btnTryAgain);
        homeBtn = findViewById(R.id.btnHome);
        dashboardBtn = findViewById(R.id.btnDashboard);

        difficultyChosen = findViewById(R.id.tvDifficultyChosen);
        categoryChosen = findViewById(R.id.tvQuizType);
        scoreObtained = findViewById(R.id.tvResultPercentage);
        resultWord = findViewById(R.id.tvResultWord);

        ivResults = findViewById(R.id.ivResults);

        Intent intent = getIntent();
        difficultyReceived = intent.getStringExtra("Difficulty");
        categoryReceived = intent.getStringExtra("Category");
        score = (intent.getIntExtra("Score", 0));

        scorePercentage = ((score /20) * 100);
        finishedScore = Math.round(scorePercentage);


        difficultyChosen.setText(difficultyReceived);
        categoryChosen.setText(categoryReceived);

        scoreObtained.setText(((finishedScore)) + " %");

        if (finishedScore == 100) {
            ivResults.setImageResource(R.drawable.outstanding);
        }
        else if (finishedScore > 70) {
            ivResults.setImageResource(R.drawable.happy);
        }
        else if (finishedScore > 50) {
            ivResults.setImageResource(R.drawable.winking);
        }
        else if (finishedScore > 20) {
            ivResults.setImageResource(R.drawable.sad);
        }
        else {
            ivResults.setImageResource(R.drawable.crying);
        }
    }

    public void onClickTryAgain(View view){

        switch (categoryReceived) {
            case "Flag": {
                Intent intent = new Intent(Results.this, FlagQuiz.class);
                intent.putExtra("Category", "Flag");
                intent.putExtra("Difficulty", difficultyReceived);
                startActivity(intent);
                break;
            }
            case "Capital": {
                Intent intent = new Intent(Results.this, CapitalQuiz.class);
                intent.putExtra("Category", "Capital");
                intent.putExtra("Difficulty", difficultyReceived);
                startActivity(intent);
                break;
            }
            case "Landmark": {
                Intent intent = new Intent(Results.this, LandmarkQuiz.class);
                intent.putExtra("Category", "Landmark");
                intent.putExtra("Difficulty", difficultyReceived);
                startActivity(intent);
                break;
            }
            case "Food": {
                Intent intent = new Intent(Results.this, FoodQuiz.class);
                intent.putExtra("Category", "Food");
                intent.putExtra("Difficulty", difficultyReceived);
                startActivity(intent);
                break;
            }
            case "Sports": {
                Intent intent = new Intent(Results.this, SportsQuiz.class);
                intent.putExtra("Category", "Sports");
                intent.putExtra("Difficulty", difficultyReceived);
                startActivity(intent);
                break;
            }
            case "Brand": {
                Intent intent = new Intent(Results.this, BrandQuiz.class);
                intent.putExtra("Category", "Brand");
                intent.putExtra("Difficulty", difficultyReceived);
                startActivity(intent);
                break;
            }
        }

    }

    public void onClickHome(View view){
        // Show confirmation dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to log out?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    // Clear user session data
                    UserLogin.clearCurrentUser();

                    // Navigate to MainActivity
                    Intent intent = new Intent(Results.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Close this activity
                })
                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void onClickDashboard(View view){

        Intent intent = new Intent(Results.this, QuizDashboard.class);
        startActivity(intent);
    }

}