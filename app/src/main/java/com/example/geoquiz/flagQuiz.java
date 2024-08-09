package com.example.geoquiz;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class flagQuiz extends AppCompatActivity {

    private ImageView ivFlagquestion;
    private List<RadioButton> radioButtons;
    private Button submitBtn;

    private String[] countriesArray = getResources().getStringArray(R.array.countries);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flag_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ivFlagquestion = findViewById(R.id.ivFlagQuestion);
        radioButtons = new ArrayList<>();
        Collections.addAll(radioButtons,
                findViewById(R.id.rb1Flag),
                findViewById(R.id.rb2Flag),
                findViewById(R.id.rb3Flag),
                findViewById(R.id.rb4Flag));

        submitBtn = findViewById(R.id.submitBtn);

        generateQuiz();

    }

    private void generateQuiz() {
        Random random = new Random();
        int correctCountryIndex = random.nextInt(countriesArray.length); //
        String correctCountry = countriesArray[correctCountryIndex];
        ivFlagquestion.setImageResource(getFlagResource(correctCountry));

        List<String> wrongAnswers = new ArrayList<>();
        while (wrongAnswers.size() < 3) {
            int wrongCountryIndex = random.nextInt(countriesArray.length);
            if (wrongCountryIndex != correctCountryIndex && !wrongAnswers.contains(countriesArray[wrongCountryIndex])) {
                wrongAnswers.add(countriesArray[wrongCountryIndex]);
            }
        }

        List<String> allAnswers = new ArrayList<>();
        allAnswers.add(correctCountry);
        allAnswers.addAll(wrongAnswers);
        Collections.shuffle(allAnswers, random);

        for (int i = 0; i < radioButtons.size(); i++) {
            radioButtons.get(i).setText(allAnswers.get(i));
        }
    }

    private int getFlagResource(String countryName) {
        String resourceName = countryName.toLowerCase().replace(" ", "_") + "_flag";
return getResources().getIdentifier(resourceName, "drawable", getPackageName());
    }
}