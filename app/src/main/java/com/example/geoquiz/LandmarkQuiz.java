package com.example.geoquiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class LandmarkQuiz extends AppCompatActivity {

    private String[] easyLandmarksArray, mediumLandmarksArray, hardLandmarksArray, countriesArray;
    private TextView tvLandmark, tvCounter;
    private ImageView ivLandmark;
    private HashMap<String, Integer> countryLandmarkImageMap;
    private HashMap<String, String> countryLandmarkMap;
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

        //All initializations
        initCountryLandmarkMap();
        initCountryLandmarkImageMap();

        tvLandmark = findViewById(R.id.tvLandmarkName);
        ivLandmark = findViewById(R.id.ivLandmark);
        tvCounter = findViewById(R.id.tvCounterLandmark);

        easyLandmarksArray = getResources().getStringArray(R.array.easyLandmarks);
        mediumLandmarksArray = getResources().getStringArray(R.array.mediumLandmarks);
        hardLandmarksArray = getResources().getStringArray(R.array.hardLandmarks);
        countriesArray = getResources().getStringArray(R.array.countries);

        radioButtons = new ArrayList<>();
        // Everything to do with the Radio Buttons
        {
            // Initializing all Radio Buttons into the radioButtons Array List
            RadioButton rbLandmark1 = findViewById(R.id.rbLandmark1); RadioButton rbLandmark2 = findViewById(R.id.rbLandmark2);
            RadioButton rbLandmark3 = findViewById(R.id.rbLandmark3); RadioButton rbLandmark4 = findViewById(R.id.rbLandmark4);
            RadioButton rbLandmark5 = findViewById(R.id.rbLandmark5); RadioButton rbLandmark6 = findViewById(R.id.rbLandmark6);

            // Add RadioButtons to the list
            radioButtons.add(rbLandmark1); radioButtons.add(rbLandmark2);
            radioButtons.add(rbLandmark3); radioButtons.add(rbLandmark4);
            radioButtons.add(rbLandmark5); radioButtons.add(rbLandmark6);

            // Set OnCheckedChangeListener for each RadioButton
            CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
                if (isChecked && lastCheckedRadioButton != null && lastCheckedRadioButton.getId() != buttonView.getId())
                {   // Uncheck the previously checked RadioButton
                    lastCheckedRadioButton.setChecked(false);
                }   // Update the lastCheckedRadioButton variable
                lastCheckedRadioButton = isChecked ? (RadioButton) buttonView : null;
            };

            rbLandmark1.setOnCheckedChangeListener(listener); rbLandmark2.setOnCheckedChangeListener(listener);
            rbLandmark3.setOnCheckedChangeListener(listener); rbLandmark4.setOnCheckedChangeListener(listener);
            rbLandmark5.setOnCheckedChangeListener(listener); rbLandmark6.setOnCheckedChangeListener(listener);

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


        //ArrayList<String> countries = DatabaseHelper.getCountriesWithDifficultyOne(LandmarkQuiz.this);

        //Log.d("TAG", countries.toString());



        List<String> usedLandmarks = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < 20; i++) {

            QuizQuestion quizQuestion = new QuizQuestion();

            String correctLandmark;

            do {
                correctLandmark = easyLandmarksArray[random.nextInt(easyLandmarksArray.length)];
            } while (usedLandmarks.contains(correctLandmark));

            usedLandmarks.add(correctLandmark);

            quizQuestion.correctLandmark = correctLandmark;
            quizQuestion.correctLandmarkCountry = getLandmarkCountry(correctLandmark);

            List<String> wrongAnswers = new ArrayList<>();

            while (wrongAnswers.size() < 5) {

                String wrongLandmark = countriesArray[random.nextInt(countriesArray.length)];
                if (!wrongAnswers.contains(wrongLandmark) && !quizQuestion.correctLandmarkCountry.equals(wrongLandmark))
                {
                    wrongAnswers.add(wrongLandmark);
                }
            }

            List<String> allAnswers = new ArrayList<>();
            allAnswers.add(getLandmarkCountry(quizQuestion.correctLandmark));  //fix this
            allAnswers.addAll(wrongAnswers);
            Collections.shuffle(allAnswers, random);

            quizQuestion.allAnswers = allAnswers;
            quizQuestions.add(quizQuestion);
        }
    }

    private void generateMediumQuiz() {

        List<String> usedLandmarks = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < 20; i++) {

            QuizQuestion quizQuestion = new QuizQuestion();

            String correctLandmark;

            do {
                correctLandmark = mediumLandmarksArray[random.nextInt(mediumLandmarksArray.length)];
            } while (usedLandmarks.contains(correctLandmark));

            usedLandmarks.add(correctLandmark);

            quizQuestion.correctLandmark = correctLandmark;
            String correctLandmarkCountry = getLandmarkCountry(correctLandmark);

            List<String> wrongAnswers = new ArrayList<>();

            while (wrongAnswers.size() < 5) {

                String wrongLandmark = countriesArray[random.nextInt(countriesArray.length)];
                if (!wrongAnswers.contains(wrongLandmark) && !correctLandmarkCountry.equals(wrongLandmark))
                {
                    wrongAnswers.add(wrongLandmark);
                }
            }

            List<String> allAnswers = new ArrayList<>();
            allAnswers.add(getLandmarkCountry(quizQuestion.correctLandmark));  //fix this
            allAnswers.addAll(wrongAnswers);
            Collections.shuffle(allAnswers, random);

            quizQuestion.allAnswers = allAnswers;
            quizQuestions.add(quizQuestion);
        }
    }

    private void generateHardQuiz() {

        List<String> usedLandmarks = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < 20; i++) {

            QuizQuestion quizQuestion = new QuizQuestion();

            String correctLandmark;

            do {
                correctLandmark = hardLandmarksArray[random.nextInt(hardLandmarksArray.length)];
            } while (usedLandmarks.contains(correctLandmark));

            usedLandmarks.add(correctLandmark);

            quizQuestion.correctLandmark = correctLandmark;
            String correctLandmarkCountry = getLandmarkCountry(correctLandmark);

            List<String> wrongAnswers = new ArrayList<>();

            while (wrongAnswers.size() < 5) {

                String wrongLandmark = countriesArray[random.nextInt(countriesArray.length)];
                if (!wrongAnswers.contains(wrongLandmark) && !correctLandmarkCountry.equals(wrongLandmark))
                {
                    wrongAnswers.add(wrongLandmark);
                }
            }

            List<String> allAnswers = new ArrayList<>();
            allAnswers.add(getLandmarkCountry(quizQuestion.correctLandmark));  //fix this
            allAnswers.addAll(wrongAnswers);
            Collections.shuffle(allAnswers, random);

            quizQuestion.allAnswers = allAnswers;
            quizQuestions.add(quizQuestion);
        }
    }

    private void displayCurrentQuestion() {

        //Uses the currentQuestionIndex to retrieve the question to be displayed
        QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);

        //sets Landmark textView in the activity to the landmark that is the focus of the question
        tvLandmark.setText(currentQuestion.correctLandmark);

        ivLandmark.setImageResource(getLandmarkImage(currentQuestion.correctLandmark));

        //Sets the question counter on display screen (tvCounter) to number of current question
        tvCounter.setText(String.valueOf(currentQuestionIndex + 1));

        // Set the answers to the enabled radio buttons
        for (int i = 0; i < radioButtons.size(); i++)
        {
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

        if(currentQuestion.correctLandmarkCountry.equals(answer))
        {
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
            for (RadioButton radiobutton : radioButtons)
            {
                radiobutton.setChecked(false);
            }
            displayCurrentQuestion();
        }
    }

    private int getLandmarkImage(String landmark){

        Integer resourceId = countryLandmarkImageMap.get(landmark);

        return resourceId == null ? 0 : resourceId ;
    }

    private String getLandmarkCountry(String landmark){

        String resourceId = countryLandmarkMap.get(landmark);

        return resourceId == null ? "Error" : resourceId ;
    }

    private void initCountryLandmarkImageMap() {

        HashMap<String, Integer> countryLandmarkImageMap = new HashMap<>();

        countryLandmarkImageMap.put("The Eiffel Tower", R.drawable.eiffel_tower);
        countryLandmarkImageMap.put("Pyramids", R.drawable.pyramids);
        countryLandmarkImageMap.put("The Great Wall", R.drawable.the_great_wall);
        countryLandmarkImageMap.put("Colosseum", R.drawable.colosseum);
        countryLandmarkImageMap.put("Statue of Liberty", R.drawable.statue_of_liberty);
        countryLandmarkImageMap.put("Stonehenge", R.drawable.stonehenge);
        countryLandmarkImageMap.put("Mt Fuji", R.drawable.mt_fujil);
        countryLandmarkImageMap.put("Great Barrier Reef", R.drawable.great_barrier_reef);
        countryLandmarkImageMap.put("Cristo Redentor", R.drawable.cristo_redentor);
        countryLandmarkImageMap.put("Burj Khalifa", R.drawable.burj_khalifa);
        countryLandmarkImageMap.put("Taj Mahal", R.drawable.taj_mahal);
        countryLandmarkImageMap.put("Machu Picchu", R.drawable.machu_picchu);
        countryLandmarkImageMap.put("Leaning Tower of Pisa", R.drawable.leaning_tower_of_pisa);
        countryLandmarkImageMap.put("Grand Canyon", R.drawable.grand_canyon);
        countryLandmarkImageMap.put("Buckingham Palace", R.drawable.buckingham_palace);
        countryLandmarkImageMap.put("Petra", R.drawable.petra);
        countryLandmarkImageMap.put("Loch Ness", R.drawable.loch_ness);
        countryLandmarkImageMap.put("Uluru", R.drawable.uluru);
        countryLandmarkImageMap.put("Ha Long Bay", R.drawable.ha_long_bay);
        countryLandmarkImageMap.put("Mont Saint-Michel", R.drawable.mont_saint_michel);
        countryLandmarkImageMap.put("Mount Kilimanjaro", R.drawable.mount_kilamanjaro);
        countryLandmarkImageMap.put("Galapagos Islands", R.drawable.galapagos_islands);
        countryLandmarkImageMap.put("Milford Sound", R.drawable.milford_sound);
        countryLandmarkImageMap.put("Hagia Sophia", R.drawable.hagia_sophia);
        countryLandmarkImageMap.put("Angel Falls", R.drawable.angel_falls);
        countryLandmarkImageMap.put("St Basils Cathedral", R.drawable.st_basils_cathedral);
        countryLandmarkImageMap.put("Angkor Wat", R.drawable.angkor_wat);
        countryLandmarkImageMap.put("Chichén Itza", R.drawable.chich_n_itza);
        countryLandmarkImageMap.put("Sagrada Familia", R.drawable.sagrada_familia);
        countryLandmarkImageMap.put("Forbidden City", R.drawable.forbidden_city);
        countryLandmarkImageMap.put("Krakatoa", R.drawable.krakatoa);
        countryLandmarkImageMap.put("CN Radio Tower", R.drawable.cn_radio_tower);
        countryLandmarkImageMap.put("Brandenburg Gate", R.drawable.brandenburg_gate);
        countryLandmarkImageMap.put("The Red Fort", R.drawable.red_fort);
        countryLandmarkImageMap.put("Petronas Twin Towers", R.drawable.petronas_towers);
        countryLandmarkImageMap.put("Yosemite National Park", R.drawable.yosemite_national_park);
        countryLandmarkImageMap.put("Table Mountain", R.drawable.table_mountain);
        countryLandmarkImageMap.put("Neuschwanstein Castle", R.drawable.neuschwanstein_castle);
        countryLandmarkImageMap.put("Casino de Monte Carlo", R.drawable.casino_de_monte_carlo);
        countryLandmarkImageMap.put("Parthenon", R.drawable.parthenon);
        countryLandmarkImageMap.put("Okavango Delta", R.drawable.okavango_delta);
        countryLandmarkImageMap.put("Uyuni Salt Flat", R.drawable.uyuni_salt_flat);
        countryLandmarkImageMap.put("Cliffs of Moher", R.drawable.cliffs_of_moher);
        countryLandmarkImageMap.put("Moraine Lake", R.drawable.moraine_lake);
        countryLandmarkImageMap.put("Tikal", R.drawable.tikal);
        countryLandmarkImageMap.put("Lake Baikal", R.drawable.lake_baikal);
        countryLandmarkImageMap.put("Borobudur", R.drawable.borobudur);
        countryLandmarkImageMap.put("Sigiriya", R.drawable.sigiriya);
        countryLandmarkImageMap.put("Mount Kosciuszko", R.drawable.mount_kosciuszko);
        countryLandmarkImageMap.put("Torre Egger", R.drawable.torre_egger);
        countryLandmarkImageMap.put("Jungfraujoch", R.drawable.jungfraujoch);
        countryLandmarkImageMap.put("The Rijksmuseum", R.drawable.the_rijksmuseum);
        countryLandmarkImageMap.put("Wieliczka Salt Mine", R.drawable.wieliczka_salt_mine);
        countryLandmarkImageMap.put("Danakil Depression", R.drawable.danakil_depression);
        countryLandmarkImageMap.put("Zuma Rock", R.drawable.zuma_rock);
        countryLandmarkImageMap.put("Mt Nyiragongo", R.drawable.mt_nyiragongo);
        countryLandmarkImageMap.put("Fox Glacier", R.drawable.fox_glacier);
        countryLandmarkImageMap.put("St Stephens Cathedral", R.drawable.st_stephens_cathedral);
        countryLandmarkImageMap.put("Belém Tower", R.drawable.belem_tower);
        countryLandmarkImageMap.put("Chocolate Hills", R.drawable.chocolate_hills);

        this.countryLandmarkImageMap = countryLandmarkImageMap;

    }

    private void initCountryLandmarkMap() {

        HashMap<String, String> countryLandmarkMap = new HashMap<>();

        countryLandmarkMap.put("The Eiffel Tower", "France");
        countryLandmarkMap.put("Pyramids", "Egypt");
        countryLandmarkMap.put("The Great Wall", "China");
        countryLandmarkMap.put("Colosseum", "Italy");
        countryLandmarkMap.put("Statue of Liberty", "USA");
        countryLandmarkMap.put("Stonehenge", "England");
        countryLandmarkMap.put("Mt Fuji", "Japan");
        countryLandmarkMap.put("Great Barrier Reef", "Australia");
        countryLandmarkMap.put("Cristo Redentor", "Brazil");
        countryLandmarkMap.put("Burj Khalifa", "UAE");
        countryLandmarkMap.put("Taj Mahal", "India");
        countryLandmarkMap.put("Machu Picchu", "Peru");
        countryLandmarkMap.put("Leaning Tower of Pisa", "Italy");
        countryLandmarkMap.put("Grand Canyon", "USA");
        countryLandmarkMap.put("Buckingham Palace", "England");
        countryLandmarkMap.put("Petra", "Jordan");
        countryLandmarkMap.put("Loch Ness", "Scotland");
        countryLandmarkMap.put("Uluru", "Australia");
        countryLandmarkMap.put("Ha Long Bay", "Vietnam");
        countryLandmarkMap.put("Mont Saint-Michel", "France");
        countryLandmarkMap.put("Mount Kilimanjaro", "Tanzania");
        countryLandmarkMap.put("Galapagos Islands", "Ecuador");
        countryLandmarkMap.put("Milford Sound", "New Zealand");
        countryLandmarkMap.put("Hagia Sophia", "Turkey");
        countryLandmarkMap.put("Angel Falls", "Venezuela");
        countryLandmarkMap.put("St Basils Cathedral", "Russia");
        countryLandmarkMap.put("Angkor Wat", "Cambodia");
        countryLandmarkMap.put("Chichén Itza", "Mexico");
        countryLandmarkMap.put("Sagrada Familia", "Spain");
        countryLandmarkMap.put("Forbidden City", "China");
        countryLandmarkMap.put("Krakatoa", "Indonesia");
        countryLandmarkMap.put("CN Radio Tower", "Canada");
        countryLandmarkMap.put("Brandenburg Gate", "Germany");
        countryLandmarkMap.put("The Red Fort", "India");
        countryLandmarkMap.put("Petronas Twin Towers", "Malaysia");
        countryLandmarkMap.put("Yosemite National Park", "USA");
        countryLandmarkMap.put("Table Mountain", "South Africa");
        countryLandmarkMap.put("Neuschwanstein Castle", "Germany");
        countryLandmarkMap.put("Casino de Monte Carlo", "Monaco");
        countryLandmarkMap.put("Parthenon", "Greece");
        countryLandmarkMap.put("Okavango Delta", "Botswana");
        countryLandmarkMap.put("Uyuni Salt Flat", "Bolivia");
        countryLandmarkMap.put("Cliffs of Moher", "Ireland");
        countryLandmarkMap.put("Moraine Lake", "Canada");
        countryLandmarkMap.put("Tikal", "Guatemala");
        countryLandmarkMap.put("Lake Baikal", "Russia");
        countryLandmarkMap.put("Borobudur", "Indonesia");
        countryLandmarkMap.put("Sigiriya", "Sri Lanka");
        countryLandmarkMap.put("Mount Kosciuszko", "Australia");
        countryLandmarkMap.put("Torre Egger", "Argentina");
        countryLandmarkMap.put("Jungfraujoch", "Switzerland");
        countryLandmarkMap.put("The Rijksmuseum", "Netherlands");
        countryLandmarkMap.put("Wieliczka Salt Mine", "Poland");
        countryLandmarkMap.put("Danakil Depression", "Ethiopia");
        countryLandmarkMap.put("Zuma Rock", "Nigeria");
        countryLandmarkMap.put("Mt Nyiragongo", "DR Congo");
        countryLandmarkMap.put("Fox Glacier", "New Zealand");
        countryLandmarkMap.put("St Stephens Cathedral", "Austria");
        countryLandmarkMap.put("Belém Tower", "Portugal");
        countryLandmarkMap.put("Chocolate Hills", "Philippines");

        this.countryLandmarkMap = countryLandmarkMap;

    }

}