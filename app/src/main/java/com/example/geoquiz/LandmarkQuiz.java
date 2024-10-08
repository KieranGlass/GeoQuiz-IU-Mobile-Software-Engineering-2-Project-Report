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
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class LandmarkQuiz extends AppCompatActivity implements MessagePopupFragment.OnPopupDismissListener {

    private TextView tvLandmark, tvCounter;
    private ImageView ivLandmark;
    List<RadioButton> radioButtons;
    final List<QuizQuestion> quizQuestions = new ArrayList<>();
    List<QuizQuestion> generatedQuizQuestions = new ArrayList<>();
    int currentQuestionIndex = 0;
    private RadioButton lastCheckedRadioButton = null;
    private String intentDifficulty, intentCategory;
    private int difficultyId;
    int score = 0;
    String difficulty;


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

        difficulty = receivedIntent.getStringExtra("Difficulty");
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

        DatabaseHelper helper = new DatabaseHelper(LandmarkQuiz.this, null, null, DatabaseHelper.DB_VERSION);

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

                String correctFlagImage = correctLandmark.getImagePath();
                int correctRowNumber = correctLandmark.getTilemap_row();
                int correctColumnNumber = correctLandmark.getTilemap_column();

                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctLandmark = correctLandmark.getName();
                quizQuestion.correctLandmarkCountry = correctCountry;
                quizQuestion.correctImage = correctFlagImage;
                quizQuestion.correctImageRow = correctRowNumber;
                quizQuestion.correctImageColumn = correctColumnNumber;
                quizQuestion.difficultyLevel = "easy";

                // Generate wrong answers
                List<Landmark> wrongLandmarks = new ArrayList<>(landmarks);
                Set<Landmark> selectedWrongLandmarks = new HashSet<>();

                int maxAttempts = 100; // Prevent infinite loops
                while (selectedWrongLandmarks.size() < Math.min(5, wrongLandmarks.size()) && maxAttempts > 0) {
                    Landmark wrongLandmark = wrongLandmarks.get(random.nextInt(wrongLandmarks.size()));

                    boolean isValid = true;
                    if (wrongLandmark.getCountry_id() == correctLandmark.getCountry_id()) {
                        Log.d("generateEasyQuiz:", "Wrong Landmark has same country value as correct answer");
                        isValid = false;
                    } else if (selectedWrongLandmarks.stream().anyMatch(lm -> lm.getCountry_id() == wrongLandmark.getCountry_id())) {
                        Log.d("generateEasyQuiz:", "Wrong Landmark shares existing country value with previously selected wrong landmarks");
                        isValid = false;
                    }

                    if (isValid) {
                        selectedWrongLandmarks.add(wrongLandmark);
                    }

                    maxAttempts--;
                }

                if (selectedWrongLandmarks.size() < Math.min(5, wrongLandmarks.size())) {
                    Log.w("generateEasyQuiz:", "Could not find enough unique wrong landmarks");
                }

                List<Landmark> selectedWrongLandmarksList = new ArrayList<>(selectedWrongLandmarks);

                // Retrieve country names for wrong landmarks
                List<String> wrongCountryNames = selectedWrongLandmarksList.stream()
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
                quizQuestion.correctImage = correctImagePath;
                quizQuestions.add(quizQuestion);
                generatedQuizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(LandmarkQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }
    }

    private void generateMediumQuiz() {

        DatabaseHelper helper = new DatabaseHelper(LandmarkQuiz.this, null, null, DatabaseHelper.DB_VERSION);

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

                String correctFlagImage = correctLandmark.getImagePath();
                int correctRowNumber = correctLandmark.getTilemap_row();
                int correctColumnNumber = correctLandmark.getTilemap_column();

                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctLandmark = correctLandmark.getName();
                quizQuestion.correctLandmarkCountry = correctCountry;
                quizQuestion.correctImage = correctFlagImage;
                quizQuestion.correctImageRow = correctRowNumber;
                quizQuestion.correctImageColumn = correctColumnNumber;
                quizQuestion.difficultyLevel = "medium";

                // Generate wrong answers
                List<Landmark> wrongLandmarks = new ArrayList<>(landmarks);
                Set<Landmark> selectedWrongLandmarks = new HashSet<>();

                int maxAttempts = 100; // Prevent infinite loops
                while (selectedWrongLandmarks.size() < Math.min(5, wrongLandmarks.size()) && maxAttempts > 0) {
                    Landmark wrongLandmark = wrongLandmarks.get(random.nextInt(wrongLandmarks.size()));

                    boolean isValid = true;
                    if (wrongLandmark.getCountry_id() == correctLandmark.getCountry_id()) {
                        Log.d("generateEasyQuiz:", "Wrong Landmark has same country value as correct answer");
                        isValid = false;
                    } else if (selectedWrongLandmarks.stream().anyMatch(lm -> lm.getCountry_id() == wrongLandmark.getCountry_id())) {
                        Log.d("generateEasyQuiz:", "Wrong Landmark shares existing country value with previously selected wrong landmarks");
                        isValid = false;
                    }

                    if (isValid) {
                        selectedWrongLandmarks.add(wrongLandmark);
                    }

                    maxAttempts--;
                }

                if (selectedWrongLandmarks.size() < Math.min(5, wrongLandmarks.size())) {
                    Log.w("generateEasyQuiz:", "Could not find enough unique wrong landmarks");
                }

                List<Landmark> selectedWrongLandmarksList = new ArrayList<>(selectedWrongLandmarks);

                // Retrieve country names for wrong landmarks
                List<String> wrongCountryNames = selectedWrongLandmarksList.stream()
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
                quizQuestion.correctImage = correctImagePath;
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
                // Retrieve the correct country name for the correct landmark
                Landmark finalCorrectLandmark = correctLandmark; // final just for use in following lambda
                String correctCountry = countries.stream()
                        .filter(country -> country.getId() == finalCorrectLandmark.getCountry_id())
                        .findFirst()
                        .map(Country::getCountry_name)
                        .orElse("");

                String correctLandmarkImage = correctLandmark.getImagePath();
                int correctRowNumber = correctLandmark.getTilemap_row();
                int correctColumnNumber = correctLandmark.getTilemap_column();

                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctLandmark = correctLandmark.getName();
                quizQuestion.correctLandmarkCountry = correctCountry;
                quizQuestion.correctImage = correctLandmarkImage;
                quizQuestion.correctImageRow = correctRowNumber;
                quizQuestion.correctImageColumn = correctColumnNumber;
                quizQuestion.difficultyLevel = "hard";

                // Generate wrong answers
                List<Landmark> wrongLandmarks = new ArrayList<>(landmarks);
                Set<Landmark> selectedWrongLandmarks = new HashSet<>();

                int maxAttempts = 100; // Prevent infinite loops
                while (selectedWrongLandmarks.size() < Math.min(5, wrongLandmarks.size()) && maxAttempts > 0) {
                    Landmark wrongLandmark = wrongLandmarks.get(random.nextInt(wrongLandmarks.size()));

                    boolean isValid = true;
                    if (wrongLandmark.getCountry_id() == correctLandmark.getCountry_id()) {
                        Log.d("generateEasyQuiz:", "Wrong Landmark has same country value as correct answer");
                        isValid = false;
                    } else if (selectedWrongLandmarks.stream().anyMatch(lm -> lm.getCountry_id() == wrongLandmark.getCountry_id())) {
                        Log.d("generateEasyQuiz:", "Wrong Landmark shares existing country value with previously selected wrong landmarks");
                        isValid = false;
                    }

                    if (isValid) {
                        selectedWrongLandmarks.add(wrongLandmark);
                    }

                    maxAttempts--;
                }

                if (selectedWrongLandmarks.size() < Math.min(5, wrongLandmarks.size())) {
                    Log.w("generateEasyQuiz:", "Could not find enough unique wrong landmarks");
                }

                List<Landmark> selectedWrongLandmarksList = new ArrayList<>(selectedWrongLandmarks);

                // Retrieve country names for wrong landmarks
                List<String> wrongCountryNames = selectedWrongLandmarksList.stream()
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
                quizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(LandmarkQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }
    }

    private void displayCurrentQuestion() {

        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);

        // Get the correct tilemap resource ID based on the difficulty level
        int tileMapResId = ResourceUtilities.getLandmarkResourceId(currentQuestion.correctImage, currentQuestion.difficultyLevel);

        tvLandmark.setText(currentQuestion.correctLandmark);

        if (tileMapResId != -1) {
            Bitmap tileMap = BitmapFactory.decodeResource(getResources(), tileMapResId);

            // Extract the Landmark image
            Bitmap flagBitmap = ResourceUtilities.getLandmarkImage(tileMap, currentQuestion.correctImageRow, currentQuestion.correctImageColumn);

            if (flagBitmap != null) {
                ivLandmark.setImageBitmap(flagBitmap);
            } else {
                Log.e("FlagLoader", "Failed to load flag");
                ivLandmark.setImageResource(R.drawable.eiffel_tower); // Set a default image for now
            }
        } else {
            Log.e("ResourceUtil", "Unable to find tilemap resource for difficulty level " + currentQuestion.difficultyLevel);
            ivLandmark.setImageResource(R.drawable.eiffel_tower); // Set a default image
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

        if (currentQuestion.correctLandmarkCountry.equals(answer)) {
            score++;
            checkAnswer(true);

        } else {
            checkAnswer(false);
        }

        currentQuestionIndex++;

        if (currentQuestionIndex >= quizQuestions.size()) {

            DatabaseHelper helper = new DatabaseHelper(LandmarkQuiz.this, null, null, DatabaseHelper.DB_VERSION);

            int categoryId = 4;
            helper.updateUserProgress(UserLogin.getCurrentUser().getId(), categoryId, difficultyId, score);

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
    // -- Purely for testing purposes -- //
    public List<QuizQuestion> getGeneratedQuestions() {
        return Collections.unmodifiableList(generatedQuizQuestions);
    }

    public String getFirstQuestionCorrectCountry(){

        List<QuizQuestion> generatedQuizQuestions = getGeneratedQuestions();
        String correctCountry = generatedQuizQuestions.get(0).correctLandmarkCountry;
        return correctCountry;
    }

}
