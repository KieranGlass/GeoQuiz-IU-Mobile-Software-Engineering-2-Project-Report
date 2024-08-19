package com.example.geoquiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class QuizDashboard extends AppCompatActivity {


    Button flagBtn, capitalBtn, landmarkBtn;

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



        flagBtn = findViewById(R.id.flagBtn);
        capitalBtn = findViewById(R.id.capitalBtn);
        landmarkBtn = findViewById(R.id.landmarkBtn);

        flagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (QuizDashboard.this, Difficulty.class);
                intent.putExtra("Category", "Flag");
                startActivity(intent);
            }
        });

        capitalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (QuizDashboard.this, Difficulty.class);
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



    }

}
