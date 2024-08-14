package com.example.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
                case "Flag":
                    easyBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(Difficulty.this, FlagQuiz.class);
                            intent.putExtra("Difficulty", "Easy");
                            intent.putExtra("Category", category);
                            startActivity(intent);
                        }
                    });

                    mediumBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(Difficulty.this, FlagQuiz.class);
                            intent.putExtra("Difficulty", "Medium");
                            intent.putExtra("Category", category);
                            startActivity(intent);
                        }
                    });

                    hardBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(Difficulty.this, FlagQuiz.class);
                            intent.putExtra("Difficulty", "Hard");
                            intent.putExtra("Category", category);
                            startActivity(intent);
                        }
                    });
                    break;
                case "Capital":
                    easyBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(Difficulty.this, CapitalQuiz.class);
                            intent.putExtra("Difficulty", "Easy");
                            intent.putExtra("Category", category);
                            startActivity(intent);
                        }
                    });

                    mediumBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(Difficulty.this, CapitalQuiz.class);
                            intent.putExtra("Difficulty", "Medium");
                            intent.putExtra("Category", category);
                            startActivity(intent);
                        }
                    });

                    hardBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(Difficulty.this, CapitalQuiz.class);
                            intent.putExtra("Difficulty", "Hard");
                            intent.putExtra("Category", category);
                            startActivity(intent);
                        }
                    });
                    break;
                case "Landmark":
                    easyBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(Difficulty.this, LandmarkQuiz.class);
                            intent.putExtra("Difficulty", "Easy");
                            intent.putExtra("Category", category);
                            startActivity(intent);
                        }
                    });

                    mediumBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(Difficulty.this, LandmarkQuiz.class);
                            intent.putExtra("Difficulty", "Medium");
                            intent.putExtra("Category", category);
                            startActivity(intent);
                        }
                    });

                    hardBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(Difficulty.this, LandmarkQuiz.class);
                            intent.putExtra("Difficulty", "Hard");
                            intent.putExtra("Category", category);
                            startActivity(intent);
                        }
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