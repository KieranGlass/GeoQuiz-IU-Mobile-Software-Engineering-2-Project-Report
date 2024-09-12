package com.example.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Difficulty extends AppCompatActivity {

    Button easyBtn, mediumBtn, hardBtn;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_difficulty);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        easyBtn = findViewById(R.id.easyBtn);
        mediumBtn = findViewById(R.id.mediumBtn);
        hardBtn = findViewById(R.id.hardBtn);

        Intent intent = getIntent();
        category = intent.getStringExtra("Category");

        if (category != null) {
            switch (category) {

                case "Landmark":
                    easyBtn.setOnClickListener(view -> {

                        Intent intent16 = new Intent(Difficulty.this, LandmarkQuiz.class);
                        intent16.putExtra("Difficulty", "Easy");
                        intent16.putExtra("Category", category);
                        startActivity(intent16);
                    });

                    mediumBtn.setOnClickListener(view -> {

                        Intent intent15 = new Intent(Difficulty.this, LandmarkQuiz.class);
                        intent15.putExtra("Difficulty", "Medium");
                        intent15.putExtra("Category", category);
                        startActivity(intent15);
                    });

                    hardBtn.setOnClickListener(view -> {

                        Intent intent14 = new Intent(Difficulty.this, LandmarkQuiz.class);
                        intent14.putExtra("Difficulty", "Hard");
                        intent14.putExtra("Category", category);
                        startActivity(intent14);
                    });
                    break;

                case "Food":
                    easyBtn.setOnClickListener(view -> {

                        Intent intent13 = new Intent(Difficulty.this, FoodQuiz.class);
                        intent13.putExtra("Difficulty", "Easy");
                        intent13.putExtra("Category", category);
                        startActivity(intent13);
                    });

                    mediumBtn.setOnClickListener(view -> {

                        Intent intent12 = new Intent(Difficulty.this, FoodQuiz.class);
                        intent12.putExtra("Difficulty", "Medium");
                        intent12.putExtra("Category", category);
                        startActivity(intent12);
                    });

                    hardBtn.setOnClickListener(view -> {

                        Intent intent1 = new Intent(Difficulty.this, FoodQuiz.class);
                        intent1.putExtra("Difficulty", "Hard");
                        intent1.putExtra("Category", category);
                        startActivity(intent1);
                    });
                    break;

                case "Sports":
                    easyBtn.setOnClickListener(view -> {

                        Intent intent19 = new Intent(Difficulty.this, SportsQuiz.class);
                        intent19.putExtra("Difficulty", "Easy");
                        intent19.putExtra("Category", category);
                        startActivity(intent19);
                    });

                    mediumBtn.setOnClickListener(view -> {

                        Intent intent110 = new Intent(Difficulty.this, SportsQuiz.class);
                        intent110.putExtra("Difficulty", "Medium");
                        intent110.putExtra("Category", category);
                        startActivity(intent110);
                    });

                    hardBtn.setOnClickListener(view -> {

                        Intent intent112 = new Intent(Difficulty.this, SportsQuiz.class);
                        intent112.putExtra("Difficulty", "Hard");
                        intent112.putExtra("Category", category);
                        startActivity(intent112);
                    });
                    break;

                case "Brand":
                    easyBtn.setOnClickListener(view -> {

                        Intent intent114 = new Intent(Difficulty.this, BrandQuiz.class);
                        intent114.putExtra("Difficulty", "Easy");
                        intent114.putExtra("Category", category);
                        startActivity(intent114);
                    });

                    mediumBtn.setOnClickListener(view -> {

                        Intent intent113 = new Intent(Difficulty.this, BrandQuiz.class);
                        intent113.putExtra("Difficulty", "Medium");
                        intent113.putExtra("Category", category);
                        startActivity(intent113);
                    });

                    hardBtn.setOnClickListener(view -> {

                        Intent intent116 = new Intent(Difficulty.this, BrandQuiz.class);
                        intent116.putExtra("Difficulty", "Hard");
                        intent116.putExtra("Category", category);
                        startActivity(intent116);
                    });
                    break;
            }
        }
        else
        {
            Intent startAgainIntent = new Intent(Difficulty.this, MainActivity.class);
            startActivity(startAgainIntent);
        }
    }
}