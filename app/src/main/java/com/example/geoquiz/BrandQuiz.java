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

public class BrandQuiz extends AppCompatActivity implements MessagePopupFragment.OnPopupDismissListener {

    private TextView tvBrand, tvCounter;
    private ImageView ivBrand;
    private List<RadioButton> radioButtons;
    private final List<QuizQuestion> quizQuestions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private RadioButton lastCheckedRadioButton = null;
    private String intentDifficulty, intentCategory;
    private int difficultyId, categoryId = 1;
    int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_brand_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvBrand = findViewById(R.id.tvBrandName);
        ivBrand = findViewById(R.id.ivBrand);
        tvCounter = findViewById(R.id.tvCounterBrand);

        radioButtons = new ArrayList<>();
        // Everything to do with the Radio Buttons
        {
            // Initializing all Radio Buttons into the radioButtons Array List
            RadioButton rbBrand1 = findViewById(R.id.rbBrand1);
            RadioButton rbBrand2 = findViewById(R.id.rbBrand2);
            RadioButton rbBrand3 = findViewById(R.id.rbBrand3);
            RadioButton rbBrand4 = findViewById(R.id.rbBrand4);
            RadioButton rbBrand5 = findViewById(R.id.rbBrand5);
            RadioButton rbBrand6 = findViewById(R.id.rbBrand6);

            // Add RadioButtons to the list
            radioButtons.add(rbBrand1);
            radioButtons.add(rbBrand2);
            radioButtons.add(rbBrand3);
            radioButtons.add(rbBrand4);
            radioButtons.add(rbBrand5);
            radioButtons.add(rbBrand6);

            // Set OnCheckedChangeListener for each RadioButton
            CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
                if (isChecked && lastCheckedRadioButton != null && lastCheckedRadioButton.getId() != buttonView.getId()) {   // Uncheck the previously checked RadioButton
                    lastCheckedRadioButton.setChecked(false);
                }   // Update the lastCheckedRadioButton variable
                lastCheckedRadioButton = isChecked ? (RadioButton) buttonView : null;
            };

            rbBrand1.setOnCheckedChangeListener(listener);
            rbBrand2.setOnCheckedChangeListener(listener);
            rbBrand3.setOnCheckedChangeListener(listener);
            rbBrand4.setOnCheckedChangeListener(listener);
            rbBrand5.setOnCheckedChangeListener(listener);
            rbBrand6.setOnCheckedChangeListener(listener);

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

        DatabaseHelper helper = new DatabaseHelper(BrandQuiz.this, null, null, DatabaseHelper.DB_VERSION);

        List<Brand> brands = helper.fetchBrands();
        List<Country> countries = helper.fetchCountries();
        List<Brand> usedBrands = new ArrayList<>();
        Brand correctBrand;
        Random random = new Random();

        List<Brand> easyBrands = brands.stream()
                .filter(brand -> brand.getDifficulty_id() == 1)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {

            if (!easyBrands.isEmpty()) { // limits the correct answers to easy difficulty
                do {
                    // ensures no duplicate correct answers in same quiz
                    correctBrand = easyBrands.get(random.nextInt(easyBrands.size()));
                } while (usedBrands.contains(correctBrand));

                usedBrands.add(correctBrand); // Marks landmark as used for above do while next time

                // Retrieve the correct country name for the correct landmark
                Brand finalCorrectBrand = correctBrand; // final just for use in following lambda
                String correctCountry = countries.stream()
                        .filter(country -> country.getId() == finalCorrectBrand.getCountry_id())
                        .findFirst()
                        .map(Country::getCountry_name)
                        .orElse("");

                String correctBrandImage = correctBrand.getBrand_path();
                int correctRowNumber = correctBrand.getTilemap_row();
                int correctColumnNumber = correctBrand.getTilemap_column();

                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctBrand = correctBrand.getName();
                quizQuestion.correctBrandCountry = correctCountry;
                quizQuestion.correctImage = correctBrandImage;
                quizQuestion.correctImageRow = correctRowNumber;
                quizQuestion.correctImageColumn = correctColumnNumber;
                quizQuestion.difficultyLevel = "easy";

                // Generate wrong answers
                List<Brand> wrongBrands = new ArrayList<>(brands);
                wrongBrands.removeIf(brand -> usedBrands.contains(brand)); // Exclude already used Brands
                Collections.shuffle(wrongBrands, random); // Shuffle to randomize order

                // Select the first three wrong landmarks
                List<Brand> selectedWrongLandmarks = wrongBrands.subList(0, Math.min(5, wrongBrands.size()));

                // Retrieve country names for wrong landmarks
                List<String> wrongCountryNames = selectedWrongLandmarks.stream()
                        .map(brand -> countries.stream()
                                .filter(country -> country.getId() == brand.getCountry_id())
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
                quizQuestion.correctImage = correctBrandImage;
                quizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(BrandQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }
    }

    private void generateMediumQuiz() {

        DatabaseHelper helper = new DatabaseHelper(BrandQuiz.this, null, null, DatabaseHelper.DB_VERSION);

        List<Brand> brands = helper.fetchBrands();
        List<Country> countries = helper.fetchCountries();
        List<Brand> usedBrands = new ArrayList<>();
        Brand correctBrand;
        Random random = new Random();

        List<Brand> mediumBrands = brands.stream()
                .filter(brand -> brand.getDifficulty_id() == 2)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {

            if (!mediumBrands.isEmpty()) { // limits the correct answers to easy difficulty
                do {
                    // ensures no duplicate correct answers in same quiz
                    correctBrand = mediumBrands.get(random.nextInt(mediumBrands.size()));
                } while (usedBrands.contains(correctBrand));

                usedBrands.add(correctBrand); // Marks landmark as used for above do while next time

                // Retrieve the correct country name for the correct landmark
                Brand finalCorrectBrand = correctBrand; // final just for use in following lambda
                String correctCountry = countries.stream()
                        .filter(country -> country.getId() == finalCorrectBrand.getCountry_id())
                        .findFirst()
                        .map(Country::getCountry_name)
                        .orElse("");

                String correctBrandImage = correctBrand.getBrand_path();
                int correctRowNumber = correctBrand.getTilemap_row();
                int correctColumnNumber = correctBrand.getTilemap_column();

                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctBrand = correctBrand.getName();
                quizQuestion.correctBrandCountry = correctCountry;
                quizQuestion.correctImage = correctBrandImage;
                quizQuestion.correctImageRow = correctRowNumber;
                quizQuestion.correctImageColumn = correctColumnNumber;
                quizQuestion.difficultyLevel = "medium";

                // Generate wrong answers
                List<Brand> wrongBrands = new ArrayList<>(brands);
                wrongBrands.removeIf(brand -> usedBrands.contains(brand)); // Exclude already used Brands
                Collections.shuffle(wrongBrands, random); // Shuffle to randomize order

                // Select the first three wrong landmarks
                List<Brand> selectedWrongLandmarks = wrongBrands.subList(0, Math.min(5, wrongBrands.size()));

                // Retrieve country names for wrong landmarks
                List<String> wrongCountryNames = selectedWrongLandmarks.stream()
                        .map(brand -> countries.stream()
                                .filter(country -> country.getId() == brand.getCountry_id())
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
                quizQuestion.correctImage = correctBrandImage;
                quizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(BrandQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }
    }


    private void generateHardQuiz() {

        DatabaseHelper helper = new DatabaseHelper(BrandQuiz.this, null, null, DatabaseHelper.DB_VERSION);

        List<Brand> brands = helper.fetchBrands();
        List<Country> countries = helper.fetchCountries();
        List<Brand> usedBrands = new ArrayList<>();
        Brand correctBrand;
        Random random = new Random();

        List<Brand> hardBrands = brands.stream()
                .filter(brand -> brand.getDifficulty_id() == 3)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {

            if (!hardBrands.isEmpty()) { // limits the correct answers to easy difficulty
                do {
                    // ensures no duplicate correct answers in same quiz
                    correctBrand = hardBrands.get(random.nextInt(hardBrands.size()));
                } while (usedBrands.contains(correctBrand));

                usedBrands.add(correctBrand); // Marks landmark as used for above do while next time

                // Retrieve the correct country name for the correct landmark
                Brand finalCorrectBrand = correctBrand; // final just for use in following lambda
                String correctCountry = countries.stream()
                        .filter(country -> country.getId() == finalCorrectBrand.getCountry_id())
                        .findFirst()
                        .map(Country::getCountry_name)
                        .orElse("");

                String correctBrandImage = correctBrand.getBrand_path();
                int correctRowNumber = correctBrand.getTilemap_row();
                int correctColumnNumber = correctBrand.getTilemap_column();

                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctBrand = correctBrand.getName();
                quizQuestion.correctBrandCountry = correctCountry;
                quizQuestion.correctImage = correctBrandImage;
                quizQuestion.correctImageRow = correctRowNumber;
                quizQuestion.correctImageColumn = correctColumnNumber;
                quizQuestion.difficultyLevel = "hard";

                // Generate wrong answers
                List<Brand> wrongBrands = new ArrayList<>(brands);
                wrongBrands.removeIf(sport -> usedBrands.contains(sport)); // Exclude already used Brands
                Collections.shuffle(wrongBrands, random); // Shuffle to randomize order

                // Select the first three wrong landmarks
                List<Brand> selectedWrongLandmarks = wrongBrands.subList(0, Math.min(5, wrongBrands.size()));

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
                quizQuestion.correctImage = correctBrandImage;
                quizQuestions.add(quizQuestion);

            } else {
                // return to dashboard for now
                Intent intent = new Intent(BrandQuiz.this, QuizDashboard.class);
                startActivity(intent);
            }
        }
    }

    private void displayCurrentQuestion() {

        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);

        // Get the correct tilemap resource ID based on the difficulty level
        int tileMapResId = ResourceUtilities.getBrandResourceId(currentQuestion.correctImage, currentQuestion.difficultyLevel);

        tvBrand.setText(currentQuestion.correctBrand);

        if (tileMapResId != -1) {
            Bitmap tileMap = BitmapFactory.decodeResource(getResources(), tileMapResId);

            // Extract the Landmark image
            Bitmap flagBitmap = ResourceUtilities.getBrandImage(tileMap, currentQuestion.correctImageRow, currentQuestion.correctImageColumn);

            if (flagBitmap != null) {
                ivBrand.setImageBitmap(flagBitmap);
            } else {
                Log.e("FlagLoader", "Failed to load flag");
                ivBrand.setImageResource(R.drawable.eiffel_tower); // Set a default image for now
            }
        } else {
            Log.e("ResourceUtil", "Unable to find tilemap resource for difficulty level " + currentQuestion.difficultyLevel);
            ivBrand.setImageResource(R.drawable.eiffel_tower); // Set a default image
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

        if (currentQuestion.correctBrandCountry.equals(answer)) {
            score++;
            checkAnswer(true);

        } else {
            checkAnswer(false);
        }


        currentQuestionIndex++;

        if (currentQuestionIndex >= quizQuestions.size()) {

            DatabaseHelper helper = new DatabaseHelper(BrandQuiz.this, null, null, DatabaseHelper.DB_VERSION);

            helper.updateUserProgress(UserLogin.getCurrentUser().getId(), categoryId, difficultyId, score);

            Intent intent = new Intent(BrandQuiz.this, Results.class);
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