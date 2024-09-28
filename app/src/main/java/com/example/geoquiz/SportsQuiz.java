package com.example.geoquiz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class SportsQuiz extends AppCompatActivity implements MessagePopupFragment.OnPopupDismissListener {

    private TextView tvSports, tvCounter;
    private ImageView ivSports;
    private List<RadioButton> radioButtons;
    final List<QuizQuestion> quizQuestions = new ArrayList<>();
    int currentQuestionIndex = 0;
    private RadioButton lastCheckedRadioButton = null;
    private String intentDifficulty, intentCategory;
    private int difficultyId, categoryId = 2;
    int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sports_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvSports = findViewById(R.id.tvSportsName);
        ivSports = findViewById(R.id.ivSports);
        tvCounter = findViewById(R.id.tvCounterSports);

        radioButtons = new ArrayList<>();
        // Everything to do with the Radio Buttons
        {
            // Initializing all Radio Buttons into the radioButtons Array List
            RadioButton rbSports1 = findViewById(R.id.rbSports1);
            RadioButton rbSports2 = findViewById(R.id.rbSports2);
            RadioButton rbSports3 = findViewById(R.id.rbSports3);
            RadioButton rbSports4 = findViewById(R.id.rbSports4);
            RadioButton rbSports5 = findViewById(R.id.rbSports5);
            RadioButton rbSports6 = findViewById(R.id.rbSports6);

            // Add RadioButtons to the list
            radioButtons.add(rbSports1);
            radioButtons.add(rbSports2);
            radioButtons.add(rbSports3);
            radioButtons.add(rbSports4);
            radioButtons.add(rbSports5);
            radioButtons.add(rbSports6);

            // Set OnCheckedChangeListener for each RadioButton
            CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
                if (isChecked && lastCheckedRadioButton != null && lastCheckedRadioButton.getId() != buttonView.getId()) {   // Uncheck the previously checked RadioButton
                    lastCheckedRadioButton.setChecked(false);
                }   // Update the lastCheckedRadioButton variable
                lastCheckedRadioButton = isChecked ? (RadioButton) buttonView : null;
            };

            rbSports1.setOnCheckedChangeListener(listener);
            rbSports2.setOnCheckedChangeListener(listener);
            rbSports3.setOnCheckedChangeListener(listener);
            rbSports4.setOnCheckedChangeListener(listener);
            rbSports5.setOnCheckedChangeListener(listener);
            rbSports6.setOnCheckedChangeListener(listener);

        }

        //Receive difficulty info from previous activity
        Intent receivedIntent = getIntent();

        String difficulty = receivedIntent.getStringExtra("Difficulty");
        String category = receivedIntent.getStringExtra("Category");

        intentDifficulty = difficulty;
        intentCategory = category;


        //pushes user into appropriate quiz based on difficulty choice
        if (difficulty == null || difficulty.isEmpty()) {
            difficulty = "Easy";
            intentDifficulty = "Easy";
        }

        if (category == null || category.isEmpty()) {
            category = "";
            intentCategory = "";
        }
        if (difficulty.equals("Easy")) {
            difficultyId = 1;
            generateEasyQuiz();
            displayCurrentQuestion();
        } else if (difficulty.equals("Medium")) {
            difficultyId = 2;
            generateMediumQuiz();
            displayCurrentQuestion();
        } else {
            difficultyId = 3;
            generateHardQuiz();
            displayCurrentQuestion();
        }

    }

    private void generateEasyQuiz() {

        DatabaseHelper helper = new DatabaseHelper(SportsQuiz.this, null, null, DatabaseHelper.DB_VERSION);

        List<Sports> sportsTeams = helper.fetchSports();
        List<Country> countries = helper.fetchCountries();
        List<Sports> usedSports = new ArrayList<>();
        Sports correctSportsTeam;
        Random random = new Random();

        List<Sports> easySports = sportsTeams.stream()
                .filter(sportsTeam -> sportsTeam.getDifficulty_id() == 1)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {

            if (!easySports.isEmpty()) { // limits the correct answers to easy difficulty
                do {
                    // ensures no duplicate correct answers in same quiz
                    correctSportsTeam = easySports.get(random.nextInt(easySports.size()));
                } while (usedSports.contains(correctSportsTeam));

                usedSports.add(correctSportsTeam); // Marks landmark as used for above do while next time

                // Retrieve the correct country name for the correct landmark
                Sports finalCorrectLandmark = correctSportsTeam; // final just for use in following lambda
                String correctCountry = countries.stream()
                        .filter(country -> country.getId() == finalCorrectLandmark.getCountry_id())
                        .findFirst()
                        .map(Country::getCountry_name)
                        .orElse("");

                String correctBadgeImage = correctSportsTeam.getSport_path();
                int correctRowNumber = correctSportsTeam.getTilemap_row();
                int correctColumnNumber = correctSportsTeam.getTilemap_column();

                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctSportsTeam = correctSportsTeam.getName();
                quizQuestion.correctSportsTeamCountry = correctCountry;
                quizQuestion.correctImage = correctBadgeImage;
                quizQuestion.correctImageRow = correctRowNumber;
                quizQuestion.correctImageColumn = correctColumnNumber;
                quizQuestion.difficultyLevel = "easy";

                // Generate wrong answers
                List<Sports> wrongSportsTeams = new ArrayList<>(sportsTeams);
                wrongSportsTeams.removeIf(sport -> usedSports.contains(sport)); // Exclude already used landmarks
                Collections.shuffle(wrongSportsTeams, random); // Shuffle to randomize order

                // Select the first three wrong landmarks
                List<Sports> selectedWrongLandmarks = wrongSportsTeams.subList(0, Math.min(5, wrongSportsTeams.size()));

                // Retrieve country names for wrong landmarks
                List<String> wrongCountryNames = selectedWrongLandmarks.stream()
                        .map(sport -> countries.stream()
                                .filter(country -> country.getId() == sport.getCountry_id())
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
                quizQuestion.correctImage = correctBadgeImage;
                quizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(SportsQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }
    }

    private void generateMediumQuiz() {

        DatabaseHelper helper = new DatabaseHelper(SportsQuiz.this, null, null, DatabaseHelper.DB_VERSION);

        List<Sports> sportsTeams = helper.fetchSports();
        List<Country> countries = helper.fetchCountries();
        List<Sports> usedSports = new ArrayList<>();
        Sports correctSportsTeam;
        Random random = new Random();

        List<Sports> mediumSports = sportsTeams.stream()
                .filter(sportsTeam -> sportsTeam.getDifficulty_id() == 2)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {

            if (!mediumSports.isEmpty()) { // limits the correct answers to easy difficulty
                do {
                    // ensures no duplicate correct answers in same quiz
                    correctSportsTeam = mediumSports.get(random.nextInt(mediumSports.size()));
                } while (usedSports.contains(correctSportsTeam));

                usedSports.add(correctSportsTeam); // Marks landmark as used for above do while next time
                String correctImagePath = correctSportsTeam.getSport_path();
                // Retrieve the correct country name for the correct landmark
                Sports finalCorrectLandmark = correctSportsTeam; // final just for use in following lambda
                String correctCountry = countries.stream()
                        .filter(country -> country.getId() == finalCorrectLandmark.getCountry_id())
                        .findFirst()
                        .map(Country::getCountry_name)
                        .orElse("");

                String correctBadgeImage = correctSportsTeam.getSport_path();
                int correctRowNumber = correctSportsTeam.getTilemap_row();
                int correctColumnNumber = correctSportsTeam.getTilemap_column();

                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctSportsTeam = correctSportsTeam.getName();
                quizQuestion.correctSportsTeamCountry = correctCountry;
                quizQuestion.correctImage = correctBadgeImage;
                quizQuestion.correctImageRow = correctRowNumber;
                quizQuestion.correctImageColumn = correctColumnNumber;
                quizQuestion.difficultyLevel = "medium";

                // Generate wrong answers
                List<Sports> wrongSportsTeams = new ArrayList<>(sportsTeams);
                wrongSportsTeams.removeIf(sport -> usedSports.contains(sport)); // Exclude already used landmarks
                Collections.shuffle(wrongSportsTeams, random); // Shuffle to randomize order

                // Select the first three wrong landmarks
                List<Sports> selectedWrongLandmarks = wrongSportsTeams.subList(0, Math.min(5, wrongSportsTeams.size()));

                // Retrieve country names for wrong landmarks
                List<String> wrongCountryNames = selectedWrongLandmarks.stream()
                        .map(sport -> countries.stream()
                                .filter(country -> country.getId() == sport.getCountry_id())
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
                quizQuestion.correctImage = correctBadgeImage;
                quizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(SportsQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }
    }


    private void generateHardQuiz() {

        DatabaseHelper helper = new DatabaseHelper(SportsQuiz.this, null, null, DatabaseHelper.DB_VERSION);

        List<Sports> sportsTeams = helper.fetchSports();
        List<Country> countries = helper.fetchCountries();
        List<Sports> usedSports = new ArrayList<>();
        Sports correctSportsTeam;
        Random random = new Random();

        List<Sports> hardSports = sportsTeams.stream()
                .filter(sportsTeam -> sportsTeam.getDifficulty_id() == 3)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {

            if (!hardSports.isEmpty()) { // limits the correct answers to easy difficulty
                do {
                    // ensures no duplicate correct answers in same quiz
                    correctSportsTeam = hardSports.get(random.nextInt(hardSports.size()));
                } while (usedSports.contains(correctSportsTeam));

                usedSports.add(correctSportsTeam); // Marks landmark as used for above do while next time
                String correctImagePath = correctSportsTeam.getSport_path();
                // Retrieve the correct country name for the correct landmark
                Sports finalCorrectLandmark = correctSportsTeam; // final just for use in following lambda
                String correctCountry = countries.stream()
                        .filter(country -> country.getId() == finalCorrectLandmark.getCountry_id())
                        .findFirst()
                        .map(Country::getCountry_name)
                        .orElse("");

                String correctBadgeImage = correctSportsTeam.getSport_path();
                int correctRowNumber = correctSportsTeam.getTilemap_row();
                int correctColumnNumber = correctSportsTeam.getTilemap_column();

                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctSportsTeam = correctSportsTeam.getName();
                quizQuestion.correctSportsTeamCountry = correctCountry;
                quizQuestion.correctImage = correctBadgeImage;
                quizQuestion.correctImageRow = correctRowNumber;
                quizQuestion.correctImageColumn = correctColumnNumber;
                quizQuestion.difficultyLevel = "hard";

                // Generate wrong answers
                List<Sports> wrongSportsTeams = new ArrayList<>(sportsTeams);
                wrongSportsTeams.removeIf(sport -> usedSports.contains(sport)); // Exclude already used landmarks
                Collections.shuffle(wrongSportsTeams, random); // Shuffle to randomize order

                // Select the first three wrong landmarks
                List<Sports> selectedWrongLandmarks = wrongSportsTeams.subList(0, Math.min(5, wrongSportsTeams.size()));

                // Retrieve country names for wrong landmarks
                List<String> wrongCountryNames = selectedWrongLandmarks.stream()
                        .map(sport -> countries.stream()
                                .filter(country -> country.getId() == sport.getCountry_id())
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
                quizQuestion.correctImage = correctBadgeImage;
                quizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(SportsQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }
    }

    private void displayCurrentQuestion() {

        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);

        // Get the correct tilemap resource ID based on the difficulty level
        int tileMapResId = ResourceUtilities.getSportsResourceId(currentQuestion.correctImage, currentQuestion.difficultyLevel);

        tvSports.setText(currentQuestion.correctSportsTeam);

        if (tileMapResId != -1) {
            Bitmap tileMap = BitmapFactory.decodeResource(getResources(), tileMapResId);

            // Extract the Landmark image
            Bitmap flagBitmap = ResourceUtilities.getSportsImage(tileMap, currentQuestion.correctImageRow, currentQuestion.correctImageColumn);

            if (flagBitmap != null) {
                ivSports.setImageBitmap(flagBitmap);
            } else {
                Log.e("FlagLoader", "Failed to load flag");
                ivSports.setImageResource(R.drawable.eiffel_tower); // Set a default image for now
            }
        } else {
            Log.e("ResourceUtil", "Unable to find tilemap resource for difficulty level " + currentQuestion.difficultyLevel);
            ivSports.setImageResource(R.drawable.eiffel_tower); // Set a default image
        }

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

        if (currentQuestion.correctSportsTeamCountry.equals(answer)) {
            score++;
            checkAnswer(true);

        } else {
            checkAnswer(false);
        }

        currentQuestionIndex++;

        if (currentQuestionIndex >= quizQuestions.size()) {

            DatabaseHelper helper = new DatabaseHelper(SportsQuiz.this, null, null, DatabaseHelper.DB_VERSION);

            helper.updateUserProgress(UserLogin.getCurrentUser().getId(), categoryId, difficultyId, score);

            Intent intent = new Intent(SportsQuiz.this, Results.class);
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