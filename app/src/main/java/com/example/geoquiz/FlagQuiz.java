package com.example.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
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

public class FlagQuiz extends AppCompatActivity {


    //TODO ADD FLAGS FOR NEWLY ADDED COUNTRIES, START IMPLEMENTING TILE MAPS IF CAN SOLVE SIZE ISSUE

    //TODO ADD SOME SORT OF FUNCTIONALITY AROUND THE CONTINENTS - POSSIBLE A CONTINENT MODE?
    private ImageView ivFlagQuestion;
    private TextView tvCounter;
    private List<RadioButton> radioButtons;
    private final List<QuizQuestion> quizQuestions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private RadioButton lastCheckedRadioButton = null;
    private String intentDifficulty, intentCategory;
    int score = 0;

    // TODO - Fix issue where certain nations (Dominican Republic) are too long for radio button

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

        // Initializations

        tvCounter = findViewById(R.id.tvCounter);
        ivFlagQuestion = findViewById(R.id.ivFlagQuestion);

        radioButtons = new ArrayList<>();
        // Everything to do with the Radio Buttons
        {
            // Initializing all Radio Buttons into the radioButtons Array List
            RadioButton rbFlag1 = findViewById(R.id.rb1Flag); RadioButton rbFlag2 = findViewById(R.id.rb2Flag);
            RadioButton rbFlag3 = findViewById(R.id.rb3Flag); RadioButton rbFlag4 = findViewById(R.id.rb4Flag);
            RadioButton rbFlag5 = findViewById(R.id.rb5Flag); RadioButton rbFlag6 = findViewById(R.id.rb6Flag);

            // Add RadioButtons to the list
            radioButtons.add(rbFlag1); radioButtons.add(rbFlag2);
            radioButtons.add(rbFlag3); radioButtons.add(rbFlag4);
            radioButtons.add(rbFlag5); radioButtons.add(rbFlag6);

            // Set OnCheckedChangeListener for each RadioButton
            CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
                if (isChecked && lastCheckedRadioButton != null && lastCheckedRadioButton.getId() != buttonView.getId())
                {   // Uncheck the previously checked RadioButton
                    lastCheckedRadioButton.setChecked(false);
                }   // Update the lastCheckedRadioButton variable
                lastCheckedRadioButton = isChecked ? (RadioButton) buttonView : null;
            };

            rbFlag1.setOnCheckedChangeListener(listener); rbFlag2.setOnCheckedChangeListener(listener);
            rbFlag3.setOnCheckedChangeListener(listener); rbFlag4.setOnCheckedChangeListener(listener);
            rbFlag5.setOnCheckedChangeListener(listener); rbFlag6.setOnCheckedChangeListener(listener);
        }

        //Receive difficulty info from previous activity
        Intent receivedIntent = getIntent();

        String difficulty = receivedIntent.getStringExtra("Difficulty");
        String category = receivedIntent.getStringExtra("Category");

        intentDifficulty = difficulty;
        intentCategory = category;

        //pushes user into appropriate quiz based on difficulty choice
        assert difficulty != null;
        if (difficulty.equals("Easy"))
        {
            generateEasyQuiz();
            displayCurrentQuestion();
        }
        else if (difficulty.equals("Medium"))
        {
            generateMediumQuiz();
            displayCurrentQuestion();
        }
        else
        {
            generateHardQuiz();
            displayCurrentQuestion();
        }

    }

    private void generateEasyQuiz() {

        DatabaseHelper helper = new DatabaseHelper(FlagQuiz.this, null, null, DatabaseHelper.DB_VERSION);

        List<Country> countries = helper.fetchCountries();
        List<Country> usedFlags = new ArrayList<>();
        Country correctCountry;
        Random random = new Random();

        List<Country> easyFlags = countries.stream()
                .filter(landmark -> landmark.getFlag_difficulty() == 1)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {
            if (!easyFlags.isEmpty()) { // limits the correct answers to easy difficulty
                do {
                    // ensures no duplicate correct answers in same quiz
                    correctCountry = easyFlags.get(random.nextInt(easyFlags.size()));
                } while (usedFlags.contains(correctCountry));

                usedFlags.add(correctCountry); // Marks landmark as used for above do while next time
                String correctFlagImage = correctCountry.getFlag_path();
                // Retrieve the correct country name for the correct landmark


                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctCountry = correctCountry.getCountry_name();
                quizQuestion.correctImagePath = correctFlagImage;

                // Generate wrong answers
                List<Country> wrongCountries = new ArrayList<>(countries);
                wrongCountries.removeIf(landmark -> usedFlags.contains(landmark)); // Exclude already used landmarks
                Collections.shuffle(wrongCountries, random); // Shuffle to randomize order

                // Select the first three wrong landmarks
                List<Country> selectedWrongCountries = wrongCountries.subList(0, Math.min(5, wrongCountries.size()));

                List<String> wrongCountryNames = new ArrayList<>();

                for (Country country : selectedWrongCountries) {
                    wrongCountryNames.add(country.getCountry_name());
                }
                // Combine correct and wrong answers
                List<String> allAnswers = new ArrayList<>();
                allAnswers.add(quizQuestion.correctCountry); // Assuming correctCountry contains the correct country name
                allAnswers.addAll(wrongCountryNames);

                // Shuffle all answers
                Collections.shuffle(allAnswers, random);

                quizQuestion.allAnswers = allAnswers;
                quizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(FlagQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }

    }

    private void generateMediumQuiz() {

    DatabaseHelper helper = new DatabaseHelper(FlagQuiz.this, null, null, DatabaseHelper.DB_VERSION);

    List<Country> countries = helper.fetchCountries();
    List<Country> usedFlags = new ArrayList<>();
    Country correctCountry;
    Random random = new Random();

    List<Country> mediumFlags = countries.stream()
            .filter(landmark -> landmark.getFlag_difficulty() == 2)
            .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {
        if (!mediumFlags.isEmpty()) { // limits the correct answers to easy difficulty
            do {
                // ensures no duplicate correct answers in same quiz
                correctCountry = mediumFlags.get(random.nextInt(mediumFlags.size()));
            } while (usedFlags.contains(correctCountry));

            usedFlags.add(correctCountry); // Marks landmark as used for above do while next time
            String correctFlagImage = correctCountry.getFlag_path();
            // Retrieve the correct country name for the correct landmark


            QuizQuestion quizQuestion = new QuizQuestion();
            quizQuestion.correctCountry = correctCountry.getCountry_name();
            quizQuestion.correctImagePath = correctFlagImage;

            // Generate wrong answers
            List<Country> wrongCountries = new ArrayList<>(countries);
            wrongCountries.removeIf(landmark -> usedFlags.contains(landmark)); // Exclude already used landmarks
            Collections.shuffle(wrongCountries, random); // Shuffle to randomize order

            // Select the first three wrong landmarks
            List<Country> selectedWrongCountries = wrongCountries.subList(0, Math.min(5, wrongCountries.size()));

            List<String> wrongCountryNames = new ArrayList<>();

            for (Country country : selectedWrongCountries) {
                wrongCountryNames.add(country.getCountry_name());
            }
            // Combine correct and wrong answers
            List<String> allAnswers = new ArrayList<>();
            allAnswers.add(quizQuestion.correctCountry); // Assuming correctCountry contains the correct country name
            allAnswers.addAll(wrongCountryNames);

            // Shuffle all answers
            Collections.shuffle(allAnswers, random);

            quizQuestion.allAnswers = allAnswers;
            quizQuestions.add(quizQuestion);

        } else {
            // return to dashboard for now
            Intent intent = new Intent(FlagQuiz.this, QuizDashboard.class);
            startActivity(intent);
        }
    }

}

    private void generateHardQuiz() {

        DatabaseHelper helper = new DatabaseHelper(FlagQuiz.this, null, null, DatabaseHelper.DB_VERSION);

        List<Country> countries = helper.fetchCountries();
        List<Country> usedFlags = new ArrayList<>();
        Country correctCountry;
        Random random = new Random();

        List<Country> hardFlags = countries.stream()
                .filter(landmark -> landmark.getFlag_difficulty() == 3)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {
            if (!hardFlags.isEmpty()) { // limits the correct answers to easy difficulty
                do {
                    // ensures no duplicate correct answers in same quiz
                    correctCountry = hardFlags.get(random.nextInt(hardFlags.size()));
                } while (usedFlags.contains(correctCountry));

                usedFlags.add(correctCountry); // Marks landmark as used for above do while next time
                String correctFlagImage = correctCountry.getFlag_path();
                // Retrieve the correct country name for the correct landmark


                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctCountry = correctCountry.getCountry_name();
                quizQuestion.correctImagePath = correctFlagImage;

                // Generate wrong answers
                List<Country> wrongCountries = new ArrayList<>(countries);
                wrongCountries.removeIf(landmark -> usedFlags.contains(landmark)); // Exclude already used landmarks
                Collections.shuffle(wrongCountries, random); // Shuffle to randomize order

                // Select the first three wrong landmarks
                List<Country> selectedWrongCountries = wrongCountries.subList(0, Math.min(5, wrongCountries.size()));

                List<String> wrongCountryNames = new ArrayList<>();

                for (Country country : selectedWrongCountries) {
                    wrongCountryNames.add(country.getCountry_name());
                }
                // Combine correct and wrong answers
                List<String> allAnswers = new ArrayList<>();
                allAnswers.add(quizQuestion.correctCountry); // Assuming correctCountry contains the correct country name
                allAnswers.addAll(wrongCountryNames);

                // Shuffle all answers
                Collections.shuffle(allAnswers, random);

                quizQuestion.allAnswers = allAnswers;
                quizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(FlagQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }

    }

    private void displayCurrentQuestion() {

        //Uses the currentQuestionIndex to retrieve the question to be displayed
        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);

        //sets flag image in the image view element (ivFlagQuestion)
        ivFlagQuestion.setImageResource(ResourceUtilities.getFlagResourceId(currentQuestion.correctImagePath));

        //Sets the question counter on display screen (tvCounter) to number of current question
        tvCounter.setText(String.valueOf(currentQuestionIndex + 1));

        //Sets the answers to the radio buttons
        for (int i = 0; i < radioButtons.size(); i++) {
            radioButtons.get(i).setText(currentQuestion.allAnswers.get(i));
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

        if(currentQuestion.correctCountry.equals(answer))
        {
            score++;
        }

        currentQuestionIndex++;

        if (currentQuestionIndex >= quizQuestions.size()) {
            // show results , new activity?

            Intent intent = new Intent(FlagQuiz.this, Results.class);
            intent.putExtra("Difficulty", intentDifficulty);
            intent.putExtra("Category", intentCategory);
            intent.putExtra("Score", score);
            startActivity(intent);

        } else {
            // unchecks all buttons for next question
            for (RadioButton radiobutton : radioButtons)
            {
                radiobutton.setChecked(false);
            }
            displayCurrentQuestion();
        }
    }

}
