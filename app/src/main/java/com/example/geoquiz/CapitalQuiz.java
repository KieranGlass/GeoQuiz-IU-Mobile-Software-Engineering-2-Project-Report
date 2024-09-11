package com.example.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CapitalQuiz extends AppCompatActivity implements MessagePopupFragment.OnPopupDismissListener {

    private TextView tvCapital, tvCounter;
    private List<RadioButton> radioButtons;
    private final List<QuizQuestion> quizQuestions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private RadioButton lastCheckedRadioButton = null;
    private String intentDifficulty, intentCategory;
    private int difficultyId, categoryId = 5;

    int score = 0;

    // TODO ALSO, PAGE LOOKS VERY BAD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_capital_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvCapital = findViewById(R.id.tvCapitalName);
        tvCounter = findViewById(R.id.tvCounterCapital);

        radioButtons = new ArrayList<>();
        // Everything to do with the Radio Buttons
                {
                    // Initializing all Radio Buttons into the radioButtons Array List
                    RadioButton rb1Capital = findViewById(R.id.rb1Capital); RadioButton rb2Capital = findViewById(R.id.rb2Capital);
                    RadioButton rb3Capital = findViewById(R.id.rb3Capital); RadioButton rb4Capital = findViewById(R.id.rb4Capital);
                    RadioButton rb5Capital = findViewById(R.id.rb5Capital); RadioButton rb6Capital = findViewById(R.id.rb6Capital);
                    RadioButton rb7Capital = findViewById(R.id.rb7Capital); RadioButton rb8Capital = findViewById(R.id.rb8Capital);

                    // Add RadioButtons to the list
                    radioButtons.add(rb1Capital); radioButtons.add(rb2Capital); radioButtons.add(rb3Capital); radioButtons.add(rb4Capital);
                    radioButtons.add(rb5Capital); radioButtons.add(rb6Capital); radioButtons.add(rb7Capital); radioButtons.add(rb8Capital);

                    // Set OnCheckedChangeListener for each RadioButton
                    CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
                        if (isChecked && lastCheckedRadioButton != null && lastCheckedRadioButton.getId() != buttonView.getId())
                        {   // Uncheck the previously checked RadioButton
                            lastCheckedRadioButton.setChecked(false);
                        }   // Update the lastCheckedRadioButton variable
                        lastCheckedRadioButton = isChecked ? (RadioButton) buttonView : null;
                    };

                    rb1Capital.setOnCheckedChangeListener(listener); rb2Capital.setOnCheckedChangeListener(listener);
                    rb3Capital.setOnCheckedChangeListener(listener); rb4Capital.setOnCheckedChangeListener(listener);
                    rb5Capital.setOnCheckedChangeListener(listener); rb6Capital.setOnCheckedChangeListener(listener);
                    rb7Capital.setOnCheckedChangeListener(listener); rb8Capital.setOnCheckedChangeListener(listener);

                }

        Intent receivedIntent = getIntent();

        String difficulty = receivedIntent.getStringExtra("Difficulty");
        String category = receivedIntent.getStringExtra("Category");

        intentDifficulty = difficulty;
        intentCategory = category;

        assert difficulty != null;
        switch (difficulty) {
            case "Easy":
                difficultyId = 1;
                generateEasyQuiz();
                displayCurrentQuestion();
                break;
            case "Medium":
                difficultyId = 2;
                generateMediumQuiz();
                displayCurrentQuestion();
                break;
            case "Hard":
                difficultyId = 3;
                generateHardQuiz();
                displayCurrentQuestion();
                break;
            case "VeryHard":
                difficultyId = 4;
                generateVeryHardQuiz();
                displayCurrentQuestion();
                break;
            default:
                difficultyId = 5;
                generateImpossibleQuiz();
                displayCurrentQuestion();
                break;
        }
    }

    private void generateEasyQuiz() {

        DatabaseHelper helper = new DatabaseHelper(CapitalQuiz.this, null, null, DatabaseHelper.DB_VERSION);

        List<Country> countries = helper.fetchCountries();
        List<Country> usedCapitals = new ArrayList<>();

        Random random = new Random();

        List<Country> easyCapitals = countries.stream()
                .filter(country -> country.getCapital_difficulty() == 1)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {

            Country correctCapital;

            if (!easyCapitals.isEmpty()) {
                do {
                    correctCapital = easyCapitals.get(random.nextInt(easyCapitals.size()));
                } while (usedCapitals.contains(correctCapital));

                usedCapitals.add(correctCapital);


                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctCapital = correctCapital.getCountry_capital();
                quizQuestion.correctCapitalCountry = correctCapital.getCountry_name();

                List<String> wrongAnswers = new ArrayList<>();
                while (wrongAnswers.size() < 5) {

                    Country wrongCapital = countries.get(random.nextInt(countries.size()));
                    if (!wrongCapital.equals(correctCapital) && !wrongAnswers.contains(wrongCapital.getCountry_name()))
                    {
                        wrongAnswers.add(wrongCapital.getCountry_name());
                    }
                }

                List<String> allAnswers = new ArrayList<>();
                allAnswers.add(quizQuestion.correctCapitalCountry);
                allAnswers.addAll(wrongAnswers);
                Collections.shuffle(allAnswers, random);

                quizQuestion.allAnswers = allAnswers;
                quizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(CapitalQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }
    }

    private void generateMediumQuiz() {

        DatabaseHelper helper = new DatabaseHelper(CapitalQuiz.this, null, null, DatabaseHelper.DB_VERSION);

        List<Country> countries = helper.fetchCountries();
        List<Country> usedCapitals = new ArrayList<>();

        Random random = new Random();

        List<Country> mediumCapitals = countries.stream()
                .filter(country -> country.getCapital_difficulty() == 2)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {

            Country correctCapital;

            if (!mediumCapitals.isEmpty()) {
                do {
                    correctCapital = mediumCapitals.get(random.nextInt(mediumCapitals.size()));
                } while (usedCapitals.contains(correctCapital));

                usedCapitals.add(correctCapital);


                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctCapital = correctCapital.getCountry_capital();
                quizQuestion.correctCapitalCountry = correctCapital.getCountry_name();

                List<String> wrongAnswers = new ArrayList<>();
                while (wrongAnswers.size() < 5) {

                    Country wrongCapital = countries.get(random.nextInt(countries.size()));
                    if (!wrongCapital.equals(correctCapital) && !wrongAnswers.contains(wrongCapital.getCountry_name()))
                    {
                        wrongAnswers.add(wrongCapital.getCountry_name());
                    }
                }

                List<String> allAnswers = new ArrayList<>();
                allAnswers.add(quizQuestion.correctCapitalCountry);
                allAnswers.addAll(wrongAnswers);
                Collections.shuffle(allAnswers, random);

                quizQuestion.allAnswers = allAnswers;
                quizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(CapitalQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }
    }

    private void generateHardQuiz()  {

        DatabaseHelper helper = new DatabaseHelper(CapitalQuiz.this, null, null, DatabaseHelper.DB_VERSION);

        List<Country> countries = helper.fetchCountries();
        List<Country> usedCapitals = new ArrayList<>();

        Random random = new Random();

        List<Country> hardCapitals = countries.stream()
                .filter(country -> country.getCapital_difficulty() == 3)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {

            Country correctCapital;

            if (!hardCapitals.isEmpty()) {
                do {
                    correctCapital = hardCapitals.get(random.nextInt(hardCapitals.size()));
                } while (usedCapitals.contains(correctCapital));

                usedCapitals.add(correctCapital);


                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctCapital = correctCapital.getCountry_capital();
                quizQuestion.correctCapitalCountry = correctCapital.getCountry_name();

                List<String> wrongAnswers = new ArrayList<>();
                while (wrongAnswers.size() < 7) {

                    Country wrongCapital = countries.get(random.nextInt(countries.size()));
                    if (!wrongCapital.equals(correctCapital) && !wrongAnswers.contains(wrongCapital.getCountry_name()))
                    {
                        wrongAnswers.add(wrongCapital.getCountry_name());
                    }
                }

                List<String> allAnswers = new ArrayList<>();
                allAnswers.add(quizQuestion.correctCapitalCountry);
                allAnswers.addAll(wrongAnswers);
                Collections.shuffle(allAnswers, random);

                quizQuestion.allAnswers = allAnswers;
                quizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(CapitalQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }
    }

    private void generateVeryHardQuiz(){

        DatabaseHelper helper = new DatabaseHelper(CapitalQuiz.this, null, null, DatabaseHelper.DB_VERSION);

        List<Country> countries = helper.fetchCountries();
        List<Country> usedCapitals = new ArrayList<>();

        Random random = new Random();

        List<Country> veryHardCapitals = countries.stream()
                .filter(country -> country.getCapital_difficulty() == 4)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {

            Country correctCapital;

            if (!veryHardCapitals.isEmpty()) {
                do {
                    correctCapital = veryHardCapitals.get(random.nextInt(veryHardCapitals.size()));
                } while (usedCapitals.contains(correctCapital));

                usedCapitals.add(correctCapital);


                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctCapital = correctCapital.getCountry_capital();
                quizQuestion.correctCapitalCountry = correctCapital.getCountry_name();

                List<String> wrongAnswers = new ArrayList<>();
                while (wrongAnswers.size() < 7) {

                    Country wrongCapital = countries.get(random.nextInt(countries.size()));
                    if (!wrongCapital.equals(correctCapital) && !wrongAnswers.contains(wrongCapital.getCountry_name()))
                    {
                        wrongAnswers.add(wrongCapital.getCountry_name());
                    }
                }

                List<String> allAnswers = new ArrayList<>();
                allAnswers.add(quizQuestion.correctCapitalCountry);
                allAnswers.addAll(wrongAnswers);
                Collections.shuffle(allAnswers, random);

                quizQuestion.allAnswers = allAnswers;
                quizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(CapitalQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }
    }

    private void generateImpossibleQuiz() {

        DatabaseHelper helper = new DatabaseHelper(CapitalQuiz.this, null, null, DatabaseHelper.DB_VERSION);

        List<Country> countries = helper.fetchCountries();
        List<Country> usedCapitals = new ArrayList<>();

        Random random = new Random();

        List<Country> impossibleCapitals = countries.stream()
                .filter(country -> country.getCapital_difficulty() == 5)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {

            Country correctCapital;

            if (!impossibleCapitals.isEmpty()) {
                do {
                    correctCapital = impossibleCapitals.get(random.nextInt(impossibleCapitals.size()));
                } while (usedCapitals.contains(correctCapital));

                usedCapitals.add(correctCapital);


                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctCapital = correctCapital.getCountry_capital();
                quizQuestion.correctCapitalCountry = correctCapital.getCountry_name();

                List<String> wrongAnswers = new ArrayList<>();
                while (wrongAnswers.size() < 7) {

                    Country wrongCapital = countries.get(random.nextInt(countries.size()));
                    if (!wrongCapital.equals(correctCapital) && !wrongAnswers.contains(wrongCapital.getCountry_name()))
                    {
                        wrongAnswers.add(wrongCapital.getCountry_name());
                    }
                }

                List<String> allAnswers = new ArrayList<>();
                allAnswers.add(quizQuestion.correctCapitalCountry);
                allAnswers.addAll(wrongAnswers);
                Collections.shuffle(allAnswers, random);

                quizQuestion.allAnswers = allAnswers;
                quizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(CapitalQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }
    }

    private void displayCurrentQuestion() {

        //Uses the currentQuestionIndex to retrieve the question to be displayed
        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);

        //sets capital textView in the activity to the capital that is the focus of the question (tvCapital)
        tvCapital.setText(currentQuestion.correctCapital);

        //Sets the question counter on display screen (tvCounter) to number of current question
        tvCounter.setText(String.valueOf(currentQuestionIndex + 1));

        //Sets the answers to the radio buttons
        // Enable or disable radio buttons based on quiz level
        if (intentDifficulty.equals("Easy")) {
            for (int i = 0; i < 6; i++) { // Only enable the first 6 radio buttons for Easy quiz
                radioButtons.get(i).setVisibility(View.VISIBLE);
                radioButtons.get(i).setEnabled(true);
                radioButtons.get(i).setChecked(false);
            }
            for (int i = 6; i < 8; i++) { // Disable the last 2 radio buttons for Easy quiz
                radioButtons.get(i).setVisibility(View.GONE);
                radioButtons.get(i).setEnabled(false);
            }
        } else if (intentCategory.equals("Medium")) {
            for (int i = 0; i < 6; i++) { // Only enable the first 6 radio buttons for Medium quiz
                radioButtons.get(i).setVisibility(View.VISIBLE);
                radioButtons.get(i).setEnabled(true);
                radioButtons.get(i).setChecked(false);

            }
            for (int i = 6; i < 8; i++) { // Disable the last 2 radio buttons for Medium quiz
                radioButtons.get(i).setVisibility(View.GONE);
                radioButtons.get(i).setEnabled(false);
            }
        } else { // Hard quiz uses all 8 radio buttons
            for (RadioButton radioButton : radioButtons) {
                radioButton.setVisibility(View.VISIBLE);
                radioButton.setEnabled(true);
                radioButton.setChecked(false);
            }
        }

        // Set the answers to the enabled radio buttons
        for (int i = 0; i < radioButtons.size(); i++) {
            if (radioButtons.get(i).isEnabled()) {
                radioButtons.get(i).setText(currentQuestion.allAnswers.get(i));
            }
        }
    }

    public void onSubmitClicked(View view) {

        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);

        String answer = "";

        for (RadioButton radioButton : radioButtons)
        {
            if (radioButton.isChecked())
            {
                answer = (String) radioButton.getText();
            }
        }

        if (currentQuestion.correctCapitalCountry.equals(answer)) {
            score++;
            checkAnswer(true);

        } else {
            checkAnswer(false);
        }

        currentQuestionIndex++;

        if (currentQuestionIndex >= quizQuestions.size()) {

            DatabaseHelper helper = new DatabaseHelper(CapitalQuiz.this, null, null, DatabaseHelper.DB_VERSION);

            helper.updateUserProgress(UserLogin.getCurrentUser().getId(), categoryId, difficultyId, score);

            Intent intent = new Intent(CapitalQuiz.this, Results.class);
            intent.putExtra("Difficulty", intentDifficulty);
            intent.putExtra("Category", intentCategory);
            intent.putExtra("Score", score);
            startActivity(intent);

        } else {
            displayCurrentQuestion();
        }
    }

    @Override
    public void onPopupDismissed(boolean isCorrect) {

    }

    private void checkAnswer(boolean isCorrect) {
        String message;
        if(isCorrect) {
            message = "Correct! Well Done";
        }
        else {
            message = "Incorrect, Better luck next time!";
        }
        MessagePopupFragment.newInstance(message, isCorrect).show(getSupportFragmentManager(), "popup");
    }
}