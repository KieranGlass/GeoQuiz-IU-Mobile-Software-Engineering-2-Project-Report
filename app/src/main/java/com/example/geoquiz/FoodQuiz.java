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

public class FoodQuiz extends AppCompatActivity implements MessagePopupFragment.OnPopupDismissListener {

    private TextView tvFood, tvCounter;
    private ImageView ivFood;
    private List<RadioButton> radioButtons;
    final List<QuizQuestion> quizQuestions = new ArrayList<>();
    int currentQuestionIndex = 0;
    private RadioButton lastCheckedRadioButton = null;
    private String intentDifficulty, intentCategory;
    private int difficultyId;
    int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvFood = findViewById(R.id.tvFoodName);
        ivFood = findViewById(R.id.ivFood);
        tvCounter = findViewById(R.id.tvCounterFood);

        radioButtons = new ArrayList<>();
        // Everything to do with the Radio Buttons
        {
            // Initializing all Radio Buttons into the radioButtons Array List
            RadioButton rbFood1 = findViewById(R.id.rbFood1);
            RadioButton rbFood2 = findViewById(R.id.rbFood2);
            RadioButton rbFood3 = findViewById(R.id.rbFood3);
            RadioButton rbFood4 = findViewById(R.id.rbFood4);
            RadioButton rbFood5 = findViewById(R.id.rbFood5);
            RadioButton rbFood6 = findViewById(R.id.rbFood6);

            // Add RadioButtons to the list
            radioButtons.add(rbFood1);
            radioButtons.add(rbFood2);
            radioButtons.add(rbFood3);
            radioButtons.add(rbFood4);
            radioButtons.add(rbFood5);
            radioButtons.add(rbFood6);

            // Set OnCheckedChangeListener for each RadioButton
            CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
                if (isChecked && lastCheckedRadioButton != null && lastCheckedRadioButton.getId() != buttonView.getId()) {   // Uncheck the previously checked RadioButton
                    lastCheckedRadioButton.setChecked(false);
                }   // Update the lastCheckedRadioButton variable
                lastCheckedRadioButton = isChecked ? (RadioButton) buttonView : null;
            };

            rbFood1.setOnCheckedChangeListener(listener);
            rbFood2.setOnCheckedChangeListener(listener);
            rbFood3.setOnCheckedChangeListener(listener);
            rbFood4.setOnCheckedChangeListener(listener);
            rbFood5.setOnCheckedChangeListener(listener);
            rbFood6.setOnCheckedChangeListener(listener);

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

        DatabaseHelper helper = new DatabaseHelper(FoodQuiz.this, null, null, DatabaseHelper.DB_VERSION);

        List<Food> foods = helper.fetchFoods();
        List<Country> countries = helper.fetchCountries();
        List<Food> usedFoods = new ArrayList<>();
        Food correctFood;
        Random random = new Random();

        List<Food> easyFoods = foods.stream()
                .filter(food -> food.getDifficulty_id() == 1)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {

            if (!easyFoods.isEmpty()) { // limits the correct answers to easy difficulty
                do {
                    // ensures no duplicate correct answers in same quiz
                    correctFood = easyFoods.get(random.nextInt(easyFoods.size()));
                } while (usedFoods.contains(correctFood));

                usedFoods.add(correctFood); // Marks food as used for above do while next time

                // Retrieve the correct country name for the correct food
                Food finalCorrectFood = correctFood; // final just for use in following lambda
                String correctCountry = countries.stream()
                        .filter(country -> country.getId() == finalCorrectFood.getCountry_id())
                        .findFirst()
                        .map(Country::getCountry_name)
                        .orElse("");

                String correctFoodImage = correctFood.getFood_path();
                int correctRowNumber = correctFood.getTilemap_row();
                int correctColumnNumber = correctFood.getTilemap_column();

                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctFood = correctFood.getName();
                quizQuestion.correctFoodCountry = correctCountry;
                quizQuestion.correctImage = correctFoodImage;
                quizQuestion.correctImageRow = correctRowNumber;
                quizQuestion.correctImageColumn = correctColumnNumber;
                quizQuestion.difficultyLevel = "easy";

                // Generate wrong answers
                List<Food> wrongFoods = new ArrayList<>(foods);
                wrongFoods.removeIf(food -> usedFoods.contains(food)); // Exclude already used Brands
                Collections.shuffle(wrongFoods, random); // Shuffle to randomize order

                // Select the first three wrong landmarks
                List<Food> selectedWrongLandmarks = wrongFoods.subList(0, Math.min(5, wrongFoods.size()));

                // Retrieve country names for wrong landmarks
                List<String> wrongCountryNames = selectedWrongLandmarks.stream()
                        .map(food -> countries.stream()
                                .filter(country -> country.getId() == food.getCountry_id())
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
                quizQuestion.correctImage = correctFoodImage;
                quizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(FoodQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }
    }

    private void generateMediumQuiz() {

        DatabaseHelper helper = new DatabaseHelper(FoodQuiz.this, null, null, DatabaseHelper.DB_VERSION);

        List<Food> foods = helper.fetchFoods();
        List<Country> countries = helper.fetchCountries();
        List<Food> usedFoods = new ArrayList<>();
        Food correctFood;
        Random random = new Random();

        List<Food> mediumFoods = foods.stream()
                .filter(food -> food.getDifficulty_id() == 2)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {

            if (!mediumFoods.isEmpty()) { // limits the correct answers to easy difficulty
                do {
                    // ensures no duplicate correct answers in same quiz
                    correctFood = mediumFoods.get(random.nextInt(mediumFoods.size()));
                } while (usedFoods.contains(correctFood));

                usedFoods.add(correctFood); // Marks food as used for above do while next time

                // Retrieve the correct country name for the correct food
                Food finalCorrectFood = correctFood; // final just for use in following lambda
                String correctCountry = countries.stream()
                        .filter(country -> country.getId() == finalCorrectFood.getCountry_id())
                        .findFirst()
                        .map(Country::getCountry_name)
                        .orElse("");

                String correctFoodImage = correctFood.getFood_path();
                int correctRowNumber = correctFood.getTilemap_row();
                int correctColumnNumber = correctFood.getTilemap_column();

                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctFood = correctFood.getName();
                quizQuestion.correctFoodCountry = correctCountry;
                quizQuestion.correctImage = correctFoodImage;
                quizQuestion.correctImageRow = correctRowNumber;
                quizQuestion.correctImageColumn = correctColumnNumber;
                quizQuestion.difficultyLevel = "medium";

                // Generate wrong answers
                List<Food> wrongFoods = new ArrayList<>(foods);
                wrongFoods.removeIf(food -> usedFoods.contains(food)); // Exclude already used Brands
                Collections.shuffle(wrongFoods, random); // Shuffle to randomize order

                // Select the first three wrong landmarks
                List<Food> selectedWrongLandmarks = wrongFoods.subList(0, Math.min(5, wrongFoods.size()));

                // Retrieve country names for wrong landmarks
                List<String> wrongCountryNames = selectedWrongLandmarks.stream()
                        .map(food -> countries.stream()
                                .filter(country -> country.getId() == food.getCountry_id())
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
                quizQuestion.correctImage = correctFoodImage;
                quizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(FoodQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }
    }


    private void generateHardQuiz() {

        DatabaseHelper helper = new DatabaseHelper(FoodQuiz.this, null, null, DatabaseHelper.DB_VERSION);

        List<Food> foods = helper.fetchFoods();
        List<Country> countries = helper.fetchCountries();
        List<Food> usedFoods = new ArrayList<>();
        Food correctFood;
        Random random = new Random();

        List<Food> hardFoods = foods.stream()
                .filter(food -> food.getDifficulty_id() == 3)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {

            if (!hardFoods.isEmpty()) { // limits the correct answers to hard difficulty
                do {
                    // ensures no duplicate correct answers in same quiz
                    correctFood = hardFoods.get(random.nextInt(hardFoods.size()));
                } while (usedFoods.contains(correctFood));

                usedFoods.add(correctFood); // Marks food as used for above do while next time

                // Retrieve the correct country name for the correct food
                Food finalCorrectFood = correctFood; // final just for use in following lambda
                String correctCountry = countries.stream()
                        .filter(country -> country.getId() == finalCorrectFood.getCountry_id())
                        .findFirst()
                        .map(Country::getCountry_name)
                        .orElse("");

                String correctFoodImage = correctFood.getFood_path();
                int correctRowNumber = correctFood.getTilemap_row();
                int correctColumnNumber = correctFood.getTilemap_column();

                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctFood = correctFood.getName();
                quizQuestion.correctFoodCountry = correctCountry;
                quizQuestion.correctImage = correctFoodImage;
                quizQuestion.correctImageRow = correctRowNumber;
                quizQuestion.correctImageColumn = correctColumnNumber;
                quizQuestion.difficultyLevel = "hard";

                // Generate wrong answers
                List<Food> wrongFoods = new ArrayList<>(foods);
                wrongFoods.removeIf(food -> usedFoods.contains(food)); // Exclude already used Brands
                Collections.shuffle(wrongFoods, random); // Shuffle to randomize order

                // Select the first three wrong foods
                List<Food> selectedWrongFoods = wrongFoods.subList(0, Math.min(5, wrongFoods.size()));

                // Retrieve country names for wrong landmarks
                List<String> wrongCountryNames = selectedWrongFoods.stream()
                        .map(food -> countries.stream()
                                .filter(country -> country.getId() == food.getCountry_id())
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
                quizQuestion.correctImage = correctFoodImage;
                quizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(FoodQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }
    }

    private void displayCurrentQuestion() {

        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);

        // Get the correct tilemap resource ID based on the difficulty level
        int tileMapResId = ResourceUtilities.getFoodResourceId(currentQuestion.correctImage, currentQuestion.difficultyLevel);

        tvFood.setText(currentQuestion.correctFood);

        if (tileMapResId != -1) {
            Bitmap tileMap = BitmapFactory.decodeResource(getResources(), tileMapResId);

            // Extract the Landmark image
            Bitmap flagBitmap = ResourceUtilities.getFoodImage(tileMap, currentQuestion.correctImageRow, currentQuestion.correctImageColumn);

            if (flagBitmap != null) {
                ivFood.setImageBitmap(flagBitmap);
            } else {
                Log.e("FlagLoader", "Failed to load flag");
                ivFood.setImageResource(R.drawable.eiffel_tower); // Set a default image for now
            }
        } else {
            Log.e("ResourceUtil", "Unable to find tilemap resource for difficulty level " + currentQuestion.difficultyLevel);
            ivFood.setImageResource(R.drawable.eiffel_tower); // Set a default image
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

        if (currentQuestion.correctFoodCountry.equals(answer)) {
            score++;
            checkAnswer(true);

        } else {
            checkAnswer(false);
        }

        currentQuestionIndex++;

        if (currentQuestionIndex >= quizQuestions.size()) {
            // show results
            DatabaseHelper helper = new DatabaseHelper(FoodQuiz.this, null, null, DatabaseHelper.DB_VERSION);

            int categoryId = 3;
            helper.updateUserProgress(UserLogin.getCurrentUser().getId(), categoryId, difficultyId, score);

            Intent intent = new Intent(FoodQuiz.this, Results.class);
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