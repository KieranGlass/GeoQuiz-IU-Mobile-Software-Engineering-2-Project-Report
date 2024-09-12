package com.example.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FlagCapitalDifficulty extends AppCompatActivity {


    Button easyBtn, mediumBtn, hardBtn, veryHardBtn, impossibleBtn;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flag_capital_difficulty);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        easyBtn = findViewById(R.id.easyBtn);
        mediumBtn = findViewById(R.id.mediumBtn);
        hardBtn = findViewById(R.id.hardBtn);
        veryHardBtn = findViewById(R.id.veryHardBtn);
        impossibleBtn = findViewById(R.id.impossibleBtn);

        Intent intent = getIntent();
        category = intent.getStringExtra("Category");

        if (category != null) {
            switch (category) {
                case "Flag":
                    easyBtn.setOnClickListener(view -> {

                        Intent intent1 = new Intent(FlagCapitalDifficulty.this, FlagQuiz.class);
                        intent1.putExtra("Difficulty", "Easy");
                        intent1.putExtra("Category", category);
                        startActivity(intent1);
                    });

                    mediumBtn.setOnClickListener(view -> {

                        Intent intent12 = new Intent(FlagCapitalDifficulty.this, FlagQuiz.class);
                        intent12.putExtra("Difficulty", "Medium");
                        intent12.putExtra("Category", category);
                        startActivity(intent12);
                    });

                    hardBtn.setOnClickListener(view -> {

                        Intent intent13 = new Intent(FlagCapitalDifficulty.this, FlagQuiz.class);
                        intent13.putExtra("Difficulty", "Hard");
                        intent13.putExtra("Category", category);
                        startActivity(intent13);
                    });
                    veryHardBtn.setOnClickListener(view -> {

                        Intent intent14 = new Intent(FlagCapitalDifficulty.this, FlagQuiz.class);
                        intent14.putExtra("Difficulty", "VeryHard");
                        intent14.putExtra("Category", category);
                        startActivity(intent14);
                    });
                    impossibleBtn.setOnClickListener(view -> {

                        Intent intent15 = new Intent(FlagCapitalDifficulty.this, FlagQuiz.class);
                        intent15.putExtra("Difficulty", "Impossible");
                        intent15.putExtra("Category", category);
                        startActivity(intent15);
                    });
                    break;

                case "Capital":
                    easyBtn.setOnClickListener(view -> {

                        Intent intent16 = new Intent(FlagCapitalDifficulty.this, CapitalQuiz.class);
                        intent16.putExtra("Difficulty", "Easy");
                        intent16.putExtra("Category", category);
                        startActivity(intent16);
                    });

                    mediumBtn.setOnClickListener(view -> {

                        Intent intent17 = new Intent(FlagCapitalDifficulty.this, CapitalQuiz.class);
                        intent17.putExtra("Difficulty", "Medium");
                        intent17.putExtra("Category", category);
                        startActivity(intent17);
                    });

                    hardBtn.setOnClickListener(view -> {

                        Intent intent19 = new Intent(FlagCapitalDifficulty.this, CapitalQuiz.class);
                        intent19.putExtra("Difficulty", "Hard");
                        intent19.putExtra("Category", category);
                        startActivity(intent19);
                    });
                    veryHardBtn.setOnClickListener(view -> {

                        Intent intent18 = new Intent(FlagCapitalDifficulty.this, CapitalQuiz.class);
                        intent18.putExtra("Difficulty", "VeryHard");
                        intent18.putExtra("Category", category);
                        startActivity(intent18);
                    });
                    impossibleBtn.setOnClickListener(view -> {

                        Intent intent110 = new Intent(FlagCapitalDifficulty.this, CapitalQuiz.class);
                        intent110.putExtra("Difficulty", "Impossible");
                        intent110.putExtra("Category", category);
                        startActivity(intent110);
                    });
                    break;

            }
        }
        else
        {
            Intent startAgainIntent = new Intent(FlagCapitalDifficulty.this, MainActivity.class);
            startActivity(startAgainIntent);
        }
    }
}
