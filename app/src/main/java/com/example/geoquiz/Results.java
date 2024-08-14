package com.example.geoquiz;

import android.content.Intent;
import android.media.Image;
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
    String difficultyReceived, categoryReceived, scoreReceived;
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
        }

    }

    public void onClickHome(View view){

        Intent intent = new Intent(Results.this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickDashboard(View view){

        Intent intent = new Intent(Results.this, QuizDashboard.class);
        startActivity(intent);
    }

}