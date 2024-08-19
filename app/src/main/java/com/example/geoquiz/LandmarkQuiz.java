package com.example.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class LandmarkQuiz extends AppCompatActivity {

    private TextView tvLandmark, tvCounter;
    private ImageView ivLandmark;
    private List<RadioButton> radioButtons;
    private final List<QuizQuestion> quizQuestions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private RadioButton lastCheckedRadioButton = null;
    private String intentDifficulty, intentCategory;
    int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landmark_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvLandmark = findViewById(R.id.tvLandmarkName);
        ivLandmark = findViewById(R.id.ivLandmark);
        tvCounter = findViewById(R.id.tvCounterLandmark);

        radioButtons = new ArrayList<>();
        // Everything to do with the Radio Buttons
        {
            // Initializing all Radio Buttons into the radioButtons Array List
            RadioButton rbLandmark1 = findViewById(R.id.rbLandmark1);
            RadioButton rbLandmark2 = findViewById(R.id.rbLandmark2);
            RadioButton rbLandmark3 = findViewById(R.id.rbLandmark3);
            RadioButton rbLandmark4 = findViewById(R.id.rbLandmark4);
            RadioButton rbLandmark5 = findViewById(R.id.rbLandmark5);
            RadioButton rbLandmark6 = findViewById(R.id.rbLandmark6);

            // Add RadioButtons to the list
            radioButtons.add(rbLandmark1);
            radioButtons.add(rbLandmark2);
            radioButtons.add(rbLandmark3);
            radioButtons.add(rbLandmark4);
            radioButtons.add(rbLandmark5);
            radioButtons.add(rbLandmark6);

            // Set OnCheckedChangeListener for each RadioButton
            CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
                if (isChecked && lastCheckedRadioButton != null && lastCheckedRadioButton.getId() != buttonView.getId()) {   // Uncheck the previously checked RadioButton
                    lastCheckedRadioButton.setChecked(false);
                }   // Update the lastCheckedRadioButton variable
                lastCheckedRadioButton = isChecked ? (RadioButton) buttonView : null;
            };

            rbLandmark1.setOnCheckedChangeListener(listener);
            rbLandmark2.setOnCheckedChangeListener(listener);
            rbLandmark3.setOnCheckedChangeListener(listener);
            rbLandmark4.setOnCheckedChangeListener(listener);
            rbLandmark5.setOnCheckedChangeListener(listener);
            rbLandmark6.setOnCheckedChangeListener(listener);

        }

        //Receive difficulty info from previous activity
        Intent receivedIntent = getIntent();

        String difficulty = receivedIntent.getStringExtra("Difficulty");
        String category = receivedIntent.getStringExtra("Category");

        intentDifficulty = difficulty;
        intentCategory = category;


        //pushes user into appropriate quiz based on difficulty choice
        assert difficulty != null;
        if (difficulty.equals("Easy")) {
            generateEasyQuiz();
            displayCurrentQuestion();
        } else if (difficulty.equals("Medium")) {
            generateMediumQuiz();
            displayCurrentQuestion();
        } else {
            generateHardQuiz();
            displayCurrentQuestion();
        }

    }

    //TODO The three methods as of now work well and with the database
    //TODO but they do not completely disallow the possibility for
    //TODO duplicate wrong answers

    private void generateEasyQuiz() {

        DatabaseHelper helper = new DatabaseHelper(LandmarkQuiz.this, null, null, DatabaseHelper.DB_VERSION);
        helper.open();

        List<Landmark> landmarks = helper.fetchLandmarks();
        List<Country> countries = helper.fetchCountries();
        List<Landmark> usedLandmarks = new ArrayList<>();
        Landmark correctLandmark;
        Random random = new Random();

        List<Landmark> easyLandmarks = landmarks.stream()
                .filter(landmark -> landmark.getDifficulty_id() == 1)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {

            if (!easyLandmarks.isEmpty()) { // limits the correct answers to easy difficulty
                do {
                    // ensures no duplicate correct answers in same quiz
                    correctLandmark = easyLandmarks.get(random.nextInt(easyLandmarks.size()));
                } while (usedLandmarks.contains(correctLandmark));

                usedLandmarks.add(correctLandmark); // Marks landmark as used for above do while next time
                String correctImagePath = correctLandmark.getImagePath();
                // Retrieve the correct country name for the correct landmark
                Landmark finalCorrectLandmark = correctLandmark; // final just for use in following lambda
                String correctCountry = countries.stream()
                        .filter(country -> country.getId() == finalCorrectLandmark.getCountry_id())
                        .findFirst()
                        .map(Country::getCountry_name)
                        .orElse("");

                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctLandmark = correctLandmark.getName();
                quizQuestion.correctLandmarkCountry = correctCountry;

                // Generate wrong answers
                List<Landmark> wrongLandmarks = new ArrayList<>(landmarks);
                wrongLandmarks.removeIf(landmark -> usedLandmarks.contains(landmark)); // Exclude already used landmarks
                Collections.shuffle(wrongLandmarks, random); // Shuffle to randomize order

                // Select the first three wrong landmarks
                List<Landmark> selectedWrongLandmarks = wrongLandmarks.subList(0, Math.min(5, wrongLandmarks.size()));

                // Retrieve country names for wrong landmarks
                List<String> wrongCountryNames = selectedWrongLandmarks.stream()
                        .map(landmark -> countries.stream()
                                .filter(country -> country.getId() == landmark.getCountry_id())
                                .findFirst()
                                .map(Country::getCountry_name)
                                .orElse(""))
                        .collect(Collectors.toList());

                // Combine correct and wrong answers
                List<String> allAnswers = new ArrayList<>();
                allAnswers.add(correctCountry); // Assuming correctCountry contains the correct country name
                allAnswers.addAll(wrongCountryNames);

                // Shuffle all answers
                Collections.shuffle(allAnswers, random);

                quizQuestion.allAnswers = allAnswers;
                quizQuestion.correctImagePath = correctImagePath;
                quizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(LandmarkQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }
    }

    private void generateMediumQuiz() {

        DatabaseHelper helper = new DatabaseHelper(LandmarkQuiz.this, null, null, DatabaseHelper.DB_VERSION);
        helper.open();

        List<Landmark> landmarks = helper.fetchLandmarks();
        List<Country> countries = helper.fetchCountries();
        List<Landmark> usedLandmarks = new ArrayList<>();
        Landmark correctLandmark;
        Random random = new Random();

        List<Landmark> mediumLandmarks = landmarks.stream()
                .filter(landmark -> landmark.getDifficulty_id() == 2)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {

            if (!mediumLandmarks.isEmpty()) { // limits the correct answers to easy difficulty
                do {
                    // ensures no duplicate correct answers in same quiz
                    correctLandmark = mediumLandmarks.get(random.nextInt(mediumLandmarks.size()));
                } while (usedLandmarks.contains(correctLandmark));

                usedLandmarks.add(correctLandmark); // Marks landmark as used for above do while next time
                String correctImagePath = correctLandmark.getImagePath();
                // Retrieve the correct country name for the correct landmark
                Landmark finalCorrectLandmark = correctLandmark; // final just for use in following lambda
                String correctCountry = countries.stream()
                        .filter(country -> country.getId() == finalCorrectLandmark.getCountry_id())
                        .findFirst()
                        .map(Country::getCountry_name)
                        .orElse("");

                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctLandmark = correctLandmark.getName();
                quizQuestion.correctLandmarkCountry = correctCountry;

                // Generate wrong answers
                List<Landmark> wrongLandmarks = new ArrayList<>(landmarks);
                wrongLandmarks.removeIf(landmark -> usedLandmarks.contains(landmark)); // Exclude already used landmarks
                Collections.shuffle(wrongLandmarks, random); // Shuffle to randomize order

                // Select the first three wrong landmarks
                List<Landmark> selectedWrongLandmarks = wrongLandmarks.subList(0, Math.min(5, wrongLandmarks.size()));

                // Retrieve country names for wrong landmarks
                List<String> wrongCountryNames = selectedWrongLandmarks.stream()
                        .map(landmark -> countries.stream()
                                .filter(country -> country.getId() == landmark.getCountry_id())
                                .findFirst()
                                .map(Country::getCountry_name)
                                .orElse(""))
                        .collect(Collectors.toList());

                // Combine correct and wrong answers
                List<String> allAnswers = new ArrayList<>();
                allAnswers.add(correctCountry); // Assuming correctCountry contains the correct country name
                allAnswers.addAll(wrongCountryNames);

                // Shuffle all answers
                Collections.shuffle(allAnswers, random);

                quizQuestion.allAnswers = allAnswers;
                quizQuestion.correctImagePath = correctImagePath;
                quizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(LandmarkQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }
    }

    private void generateHardQuiz() {

        DatabaseHelper helper = new DatabaseHelper(LandmarkQuiz.this, null, null, DatabaseHelper.DB_VERSION);

        List<Landmark> landmarks = helper.fetchLandmarks();
        List<Country> countries = helper.fetchCountries();
        List<Landmark> usedLandmarks = new ArrayList<>();
        Landmark correctLandmark;
        Random random = new Random();

        List<Landmark> hardLandmarks = landmarks.stream()
                .filter(landmark -> landmark.getDifficulty_id() == 3)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {

            if (!hardLandmarks.isEmpty()) { // limits the correct answers to easy difficulty
                do {
                    // ensures no duplicate correct answers in same quiz
                    correctLandmark = hardLandmarks.get(random.nextInt(hardLandmarks.size()));
                } while (usedLandmarks.contains(correctLandmark));

                usedLandmarks.add(correctLandmark); // Marks landmark as used for above do while next time
                String correctImagePath = correctLandmark.getImagePath();
                // Retrieve the correct country name for the correct landmark
                Landmark finalCorrectLandmark = correctLandmark; // final just for use in following lambda
                String correctCountry = countries.stream()
                        .filter(country -> country.getId() == finalCorrectLandmark.getCountry_id())
                        .findFirst()
                        .map(Country::getCountry_name)
                        .orElse("");

                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctLandmark = correctLandmark.getName();
                quizQuestion.correctLandmarkCountry = correctCountry;

                // Generate wrong answers
                List<Landmark> wrongLandmarks = new ArrayList<>(landmarks);
                wrongLandmarks.removeIf(landmark -> usedLandmarks.contains(landmark)); // Exclude already used landmarks
                Collections.shuffle(wrongLandmarks, random); // Shuffle to randomize order

                // Select the first three wrong landmarks
                List<Landmark> selectedWrongLandmarks = wrongLandmarks.subList(0, Math.min(5, wrongLandmarks.size()));

                // Retrieve country names for wrong landmarks
                List<String> wrongCountryNames = selectedWrongLandmarks.stream()
                        .map(landmark -> countries.stream()
                                .filter(country -> country.getId() == landmark.getCountry_id())
                                .findFirst()
                                .map(Country::getCountry_name)
                                .orElse(""))
                        .collect(Collectors.toList());

                // Combine correct and wrong answers
                List<String> allAnswers = new ArrayList<>();
                allAnswers.add(correctCountry); // Assuming correctCountry contains the correct country name
                allAnswers.addAll(wrongCountryNames);

                // Shuffle all answers
                Collections.shuffle(allAnswers, random);

                quizQuestion.allAnswers = allAnswers;
                quizQuestion.correctImagePath = correctImagePath;
                quizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(LandmarkQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }
    }

    private void displayCurrentQuestion() {

        //Uses the currentQuestionIndex to retrieve the question to be displayed
        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);

        //sets textView in the activity to the landmark that is the focus of the question
        tvLandmark.setText(currentQuestion.correctLandmark);

        ivLandmark.setImageResource(ResourceUtilities.getResourceId(currentQuestion.correctImagePath));

        //Sets the question counter on display screen (tvCounter) to number of current question
        tvCounter.setText(String.valueOf(currentQuestionIndex + 1));

        // Set the answers to the enabled radio buttons
        for (int i = 0; i < radioButtons.size(); i++) {
            radioButtons.get(i).setText(currentQuestion.allAnswers.get(i));
        }
    }

    public void onSubmitClicked(View view) {

        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);

        String answer = "";

        for (RadioButton radioButton : radioButtons) {
            if (radioButton.isChecked()) {
                answer = (String) radioButton.getText();
            }
        }

        if (currentQuestion.correctLandmarkCountry.equals(answer)) {
            score++;
        }

        currentQuestionIndex++;

        if (currentQuestionIndex >= quizQuestions.size()) {
            // show results , new activity?

            Intent intent = new Intent(LandmarkQuiz.this, Results.class);
            intent.putExtra("Difficulty", intentDifficulty);
            intent.putExtra("Category", intentCategory);
            intent.putExtra("Score", score);
            startActivity(intent);

        } else {
            // unchecks all buttons for next question
            for (RadioButton radiobutton : radioButtons) {
                radiobutton.setChecked(false);
            }
            displayCurrentQuestion();
        }
    }

}
