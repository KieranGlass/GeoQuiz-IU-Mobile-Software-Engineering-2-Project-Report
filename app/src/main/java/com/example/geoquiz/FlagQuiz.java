package com.example.geoquiz;

import static com.example.geoquiz.ResourceUtilities.getFlagResourceId;

import android.content.Context;
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
import android.widget.Toast;

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

public class FlagQuiz extends AppCompatActivity implements MessagePopupFragment.OnPopupDismissListener {
    ImageView ivFlagQuestion;
    TextView tvCounter;
    List<RadioButton> radioButtons;
    List<QuizQuestion> quizQuestions = new ArrayList<>();
    QuizQuestion quizQuestion;
    List<QuizQuestion> generatedQuizQuestions = new ArrayList<>();
    int currentQuestionIndex = 0;
    RadioButton lastCheckedRadioButton = null;
    RadioButton rbFlag1, rbFlag2, rbFlag3, rbFlag4, rbFlag5, rbFlag6;
    private String intentDifficulty, intentCategory;
    private int difficultyId;
    int score = 0;
    String difficulty;


    // TODO - Fix issue where certain nations (Dominican Republic, Trinidad + Tobago) are too long for radio button

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
            // Initializing each individual Radio Button
            rbFlag1 = findViewById(R.id.rb1Flag); rbFlag2 = findViewById(R.id.rb2Flag);
            rbFlag3 = findViewById(R.id.rb3Flag); rbFlag4 = findViewById(R.id.rb4Flag);
            rbFlag5 = findViewById(R.id.rb5Flag); rbFlag6 = findViewById(R.id.rb6Flag);

            // Add RadioButtons to the ArrayList
            radioButtons.add(rbFlag1); radioButtons.add(rbFlag2);
            radioButtons.add(rbFlag3); radioButtons.add(rbFlag4);
            radioButtons.add(rbFlag5); radioButtons.add(rbFlag6);

            // Set OnCheckedChangeListener for each RadioButton. Ensures only one button is checked at one time
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

        difficulty = receivedIntent.getStringExtra("Difficulty");
        String category = receivedIntent.getStringExtra("Category");

        intentDifficulty = difficulty;
        intentCategory = category;

        /*
        pushes user into appropriate quiz based on difficulty choice
        TODO - Maybe look at changing structure here.
        i.e i could have just one method for all quizzes and the method itself can handle the difficulty
        as the only real difference between methods is the value of two variables inside the method
        */

        // Set default values if not provided
        if (difficulty == null || difficulty.isEmpty()) {
            difficulty = "Easy";
            intentDifficulty = "Easy";
        }

        if (category == null || category.isEmpty()) {
            category = "";
            intentCategory = "";
        }
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

    public void generateEasyQuiz() {

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
                int correctRowNumber = correctCountry.getTilemap_row();
                int correctColumnNumber = correctCountry.getTilemap_column();
                // Retrieve the correct country name


                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctCountry = correctCountry.getCountry_name();
                quizQuestion.correctImage = correctFlagImage;
                quizQuestion.correctImageRow = correctRowNumber;
                quizQuestion.correctImageColumn = correctColumnNumber;
                quizQuestion.difficultyLevel = "easy";

                // Generate wrong answers
                List<Country> wrongCountries = new ArrayList<>(countries);
                wrongCountries.removeIf(landmark -> usedFlags.contains(landmark)); // Exclude already used countries
                Collections.shuffle(wrongCountries, random); // Shuffle to randomize order

                // Select the first three wrong countries
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
                generatedQuizQuestions.add(quizQuestion);

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
            int correctRowNumber = correctCountry.getTilemap_row();
            int correctColumnNumber = correctCountry.getTilemap_column();
            // Retrieve the correct country name


            QuizQuestion quizQuestion = new QuizQuestion();
            quizQuestion.correctCountry = correctCountry.getCountry_name();
            quizQuestion.correctImage = correctFlagImage;
            quizQuestion.correctImageRow = correctRowNumber;
            quizQuestion.correctImageColumn = correctColumnNumber;
            quizQuestion.difficultyLevel = "medium";

            // Generate wrong answers
            List<Country> wrongCountries = new ArrayList<>(countries);
            wrongCountries.removeIf(landmark -> usedFlags.contains(landmark)); // Exclude already used countries
            Collections.shuffle(wrongCountries, random); // Shuffle to randomize order

            // Select the first three wrong countries
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
                int correctRowNumber = correctCountry.getTilemap_row();
                int correctColumnNumber = correctCountry.getTilemap_column();
                // Retrieve the correct country name for the correct landmark


                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctCountry = correctCountry.getCountry_name();
                quizQuestion.correctImage = correctFlagImage;
                quizQuestion.correctImageRow = correctRowNumber;
                quizQuestion.correctImageColumn = correctColumnNumber;
                quizQuestion.difficultyLevel = "hard";

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

    private void generateVeryHardQuiz() {

        DatabaseHelper helper = new DatabaseHelper(FlagQuiz.this, null, null, DatabaseHelper.DB_VERSION);

        List<Country> countries = helper.fetchCountries();
        List<Country> usedFlags = new ArrayList<>();
        Country correctCountry;
        Random random = new Random();

        List<Country> veryHardFlags = countries.stream()
                .filter(landmark -> landmark.getFlag_difficulty() == 4)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {
            if (!veryHardFlags.isEmpty()) { // limits the correct answers to easy difficulty
                do {
                    // ensures no duplicate correct answers in same quiz
                    correctCountry = veryHardFlags.get(random.nextInt(veryHardFlags.size()));
                } while (usedFlags.contains(correctCountry));

                usedFlags.add(correctCountry); // Marks landmark as used for above do while next time
                String correctFlagImage = correctCountry.getFlag_path();
                int correctRowNumber = correctCountry.getTilemap_row();
                int correctColumnNumber = correctCountry.getTilemap_column();
                // Retrieve the correct country name for the correct country


                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctCountry = correctCountry.getCountry_name();
                quizQuestion.correctImage = correctFlagImage;
                quizQuestion.correctImageRow = correctRowNumber;
                quizQuestion.correctImageColumn = correctColumnNumber;
                quizQuestion.difficultyLevel = "very_hard";

                // Generate wrong answers
                List<Country> wrongCountries = new ArrayList<>(countries);
                wrongCountries.removeIf(landmark -> usedFlags.contains(landmark)); // Exclude already used country
                Collections.shuffle(wrongCountries, random); // Shuffle to randomize order

                // Select the first five wrong countries
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

    private void generateImpossibleQuiz() {

        DatabaseHelper helper = new DatabaseHelper(FlagQuiz.this, null, null, DatabaseHelper.DB_VERSION);

        List<Country> countries = helper.fetchCountries();
        List<Country> usedFlags = new ArrayList<>();
        Country correctCountry;
        Random random = new Random();

        List<Country> impossibleFlags = countries.stream()
                .filter(landmark -> landmark.getFlag_difficulty() == 5)
                .collect(Collectors.toList());

        for (int i = 0; i < 20; i++) {
            if (!impossibleFlags.isEmpty()) { // limits the correct answers to easy difficulty
                do {
                    // ensures no duplicate correct answers in same quiz
                    correctCountry = impossibleFlags.get(random.nextInt(impossibleFlags.size()));
                } while (usedFlags.contains(correctCountry));

                usedFlags.add(correctCountry); // Marks landmark as used for above do while next time
                String correctFlagImage = correctCountry.getFlag_path();
                int correctRowNumber = correctCountry.getTilemap_row();
                int correctColumnNumber = correctCountry.getTilemap_column();
                // Retrieve the correct country name for the correct country


                QuizQuestion quizQuestion = new QuizQuestion();
                quizQuestion.correctCountry = correctCountry.getCountry_name();
                quizQuestion.correctImage = correctFlagImage;
                quizQuestion.correctImageRow = correctRowNumber;
                quizQuestion.correctImageColumn = correctColumnNumber;
                quizQuestion.difficultyLevel = "impossible";

                // Generate wrong answers
                List<Country> wrongCountries = new ArrayList<>(countries);
                wrongCountries.removeIf(landmark -> usedFlags.contains(landmark)); // Exclude already used country
                Collections.shuffle(wrongCountries, random); // Shuffle to randomize order

                // Select the first five wrong countries
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


    void displayCurrentQuestion() {
        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);

        // Get the correct tilemap resource ID based on the difficulty level
        int tileMapResId = getFlagResourceId(currentQuestion.correctImage, currentQuestion.difficultyLevel);

        if (tileMapResId != -1) {
            Bitmap tileMap = BitmapFactory.decodeResource(getResources(), tileMapResId);

            // Extract the flag image
            Bitmap flagBitmap = ResourceUtilities.getFlagImage(tileMap, currentQuestion.correctImageRow, currentQuestion.correctImageColumn);

            if (flagBitmap != null) {
                ivFlagQuestion.setImageBitmap(flagBitmap);
            } else {
                Log.e("FlagLoader", "Failed to load flag");
                ivFlagQuestion.setImageResource(R.drawable.england_flag); // Set a default flag
            }
        } else {
            Log.e("ResourceUtil", "Unable to find tilemap resource for difficulty level " + currentQuestion.difficultyLevel);
            ivFlagQuestion.setImageResource(R.drawable.england_flag); // Set a default flag
        }

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

        if (currentQuestion.correctCountry.equals(answer)) {
            score++;
            checkAnswer(true);

        } else {
            checkAnswer(false);
        }

        currentQuestionIndex++;

        if (currentQuestionIndex >= quizQuestions.size()) {

            DatabaseHelper helper = new DatabaseHelper(FlagQuiz.this, null, null, DatabaseHelper.DB_VERSION);

            int categoryId = 6;

            User currentUser = UserLogin.getCurrentUser();

            if (currentUser != null) {
                helper.updateUserProgress(currentUser.getId(), categoryId, difficultyId, score);
            } else {
                Toast.makeText(this, "No logged-in user found. Cannot update progress.", Toast.LENGTH_SHORT).show();

            }

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

    @Override
    public void onPopupDismissed(boolean isCorrect) {

    }

    void checkAnswer(boolean isCorrect) {
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
        String correctCountry = generatedQuizQuestions.get(0).correctCountry;
        return correctCountry;
    }
}
