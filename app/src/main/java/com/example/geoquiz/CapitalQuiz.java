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
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CapitalQuiz extends AppCompatActivity implements MessagePopupFragment.OnPopupDismissListener {

    private TextView tvCapital, tvCounter;
    private String[] easyCapitalsArray, mediumCapitalsArray, hardCapitalsArray, countriesArray;
    private HashMap<String, String> countryCapitalMap;
    private List<RadioButton> radioButtons;
    private final List<QuizQuestion> quizQuestions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private RadioButton lastCheckedRadioButton = null;
    private String intentDifficulty, intentCategory;

    int score = 0;

    // TODO WHOLE QUIZ ISNT WORKING PROPERLY, HARD QUIZ ONLY ONE OPERATING
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

        initCountryCapitalMap();

        tvCapital = findViewById(R.id.tvCapitalName);
        tvCounter = findViewById(R.id.tvCounterCapital);

        easyCapitalsArray = getResources().getStringArray(R.array.easyCapitals);
        mediumCapitalsArray = getResources().getStringArray(R.array.mediumCapitals);
        hardCapitalsArray = getResources().getStringArray(R.array.hardCapitals);
        countriesArray = getResources().getStringArray(R.array.countries);

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

        List<String> usedCapitals = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < 20; i++) {

            QuizQuestion quizQuestion = new QuizQuestion();

            String correctCapital;
            do {
                correctCapital = easyCapitalsArray[random.nextInt(easyCapitalsArray.length)];
            } while (usedCapitals.contains(correctCapital));

            usedCapitals.add(correctCapital);

            quizQuestion.correctCapital = correctCapital;
            String correctCapitalCountry = getCountry(correctCapital);


            List<String> wrongAnswers = new ArrayList<>();

            while (wrongAnswers.size() < 3) {

                String wrongCapital = countriesArray[random.nextInt(countriesArray.length)];
                if (!wrongCapital.equals(correctCapitalCountry) && !wrongAnswers.contains(wrongCapital))
                {
                    wrongAnswers.add(wrongCapital);
                }
            }

        List<String> allAnswers = new ArrayList<>();
        allAnswers.add(getCountry(quizQuestion.correctCapital));
        allAnswers.addAll(wrongAnswers);
        Collections.shuffle(allAnswers, random);

        quizQuestion.allAnswers = allAnswers;
        quizQuestions.add(quizQuestion);
        }
    }

    private void generateMediumQuiz() {

        List<String> usedCapitals = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < 20; i++) {

            QuizQuestion quizQuestion = new QuizQuestion();

            String correctCapital;
            do {
                correctCapital = mediumCapitalsArray[random.nextInt(mediumCapitalsArray.length)];
            } while (usedCapitals.contains(correctCapital));

            usedCapitals.add(correctCapital);

            quizQuestion.correctCapital = correctCapital;
            String correctCapitalCountry = getCountry(correctCapital);

            List<String> wrongAnswers = new ArrayList<>();
            while (wrongAnswers.size() < 5) {

                String wrongCapital = countriesArray[random.nextInt(countriesArray.length)];
                if (!wrongCapital.equals(correctCapitalCountry) && !wrongAnswers.contains(wrongCapital))
                {
                    wrongAnswers.add(wrongCapital);
                }
            }

            List<String> allAnswers = new ArrayList<>();
            allAnswers.add(getCountry(quizQuestion.correctCapital));
            allAnswers.addAll(wrongAnswers);
            Collections.shuffle(allAnswers, random);

            quizQuestion.allAnswers = allAnswers;
            quizQuestions.add(quizQuestion);
        }
    }

    private void generateHardQuiz()  {

        List<String> usedCapitals = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < 20; i++) {

            QuizQuestion quizQuestion = new QuizQuestion();

            String correctCapital;
            do {
                correctCapital = hardCapitalsArray[random.nextInt(hardCapitalsArray.length)];
            } while (usedCapitals.contains(correctCapital));

            usedCapitals.add(correctCapital);

            quizQuestion.correctCapital = correctCapital;
            quizQuestion.correctCapitalCountry = getCountry(correctCapital);

            List<String> wrongAnswers = new ArrayList<>();
            while (wrongAnswers.size() < 7) {

                String wrongCapital = countriesArray[random.nextInt(countriesArray.length)];
                if (!wrongCapital.equals(quizQuestion.correctCapitalCountry) && !wrongAnswers.contains(wrongCapital))
                {
                    wrongAnswers.add(wrongCapital);
                }
            }

            List<String> allAnswers = new ArrayList<>();
            allAnswers.add(getCountry(quizQuestion.correctCapital));
            allAnswers.addAll(wrongAnswers);
            Collections.shuffle(allAnswers, random);

            quizQuestion.allAnswers = allAnswers;
            quizQuestions.add(quizQuestion);
        }
    }

    private void initCountryCapitalMap() {

        HashMap<String, String> countryCapitalMap = new HashMap<>();

        countryCapitalMap.put("Kabul", "Afghanistan");
        countryCapitalMap.put("Tirana", "Albania");
        countryCapitalMap.put("Algiers", "Algeria");
        countryCapitalMap.put("Luanda", "Angola");
        countryCapitalMap.put("Buenos Aires", "Argentina");
        countryCapitalMap.put("Canberra", "Australia");
        countryCapitalMap.put("Vienna", "Austria");
        countryCapitalMap.put("Baku", "Azerbaijan");
        countryCapitalMap.put("Manama", "Bahrain");
        countryCapitalMap.put("Dhaka", "Bangladesh");
        countryCapitalMap.put("Bridgetown", "Barbados");
        countryCapitalMap.put("Minsk", "Belarus");
        countryCapitalMap.put("Brussels", "Belgium");
        countryCapitalMap.put("Belmopan", "Belize");
        countryCapitalMap.put("Thimphu", "Bhutan");
        countryCapitalMap.put("La Paz", "Bolivia");
        countryCapitalMap.put("Gaborone", "Botswana");
        countryCapitalMap.put("Brasilia", "Brazil");
        countryCapitalMap.put("Sofia", "Bulgaria");
        countryCapitalMap.put("Ouagadougou", "Burkina Faso");
        countryCapitalMap.put("Gitega", "Burundi");
        countryCapitalMap.put("Praia", "Cape Verde");
        countryCapitalMap.put("Phnom Penh", "Cambodia");
        countryCapitalMap.put("Yaoundé", "Cameroon");
        countryCapitalMap.put("Ottawa", "Canada");
        countryCapitalMap.put("Santiago", "Chile");
        countryCapitalMap.put("Beijing", "China");
        countryCapitalMap.put("Bogota", "Colombia");
        countryCapitalMap.put("Moroni", "Comoros");
        countryCapitalMap.put("Kinshasa", "DR Congo");
        countryCapitalMap.put("Brazzaville", "Congo");
        countryCapitalMap.put("San José", "Costa Rica");
        countryCapitalMap.put("Yamoussoukro", "Ivory Coast");
        countryCapitalMap.put("Zagreb", "Croatia");
        countryCapitalMap.put("Havana", "Cuba");
        countryCapitalMap.put("Nicosia", "Cyprus");
        countryCapitalMap.put("Prague", "Czechia");
        countryCapitalMap.put("Copenhagen", "Denmark");
        countryCapitalMap.put("Roseau", "Dominica");
        countryCapitalMap.put("Quito", "Ecuador");
        countryCapitalMap.put("Cairo", "Egypt");
        countryCapitalMap.put("London", "England");
        countryCapitalMap.put("Asmara", "Eritrea");
        countryCapitalMap.put("Tallinn", "Estonia");
        countryCapitalMap.put("Addis Ababa", "Ethiopia");
        countryCapitalMap.put("Suva", "Fiji");
        countryCapitalMap.put("Helsinki", "Finland");
        countryCapitalMap.put("Paris", "France");
        countryCapitalMap.put("Banjul", "Gambia");
        countryCapitalMap.put("Tbilisi", "Georgia");
        countryCapitalMap.put("Berlin", "Germany");
        countryCapitalMap.put("Accra", "Ghana");
        countryCapitalMap.put("Athens", "Greece");
        countryCapitalMap.put("Guatemala City", "Guatemala");
        countryCapitalMap.put("Conakry", "Guinea");
        countryCapitalMap.put("Bissau", "Guinea-Bissau");
        countryCapitalMap.put("Georgetown", "Guyana");
        countryCapitalMap.put("Port-au-Prince", "Haiti");
        countryCapitalMap.put("Tegucigalpa", "Honduras");
        countryCapitalMap.put("Budapest", "Hungary");
        countryCapitalMap.put("Reykjavik", "Iceland");
        countryCapitalMap.put("New Delhi", "India");
        countryCapitalMap.put("Jakarta", "Indonesia");
        countryCapitalMap.put("Tehran", "Iran");
        countryCapitalMap.put("Baghdad", "Iraq");
        countryCapitalMap.put("Dublin", "Ireland");
        countryCapitalMap.put("Jerusalem", "Israel");
        countryCapitalMap.put("Rome", "Italy");
        countryCapitalMap.put("Kingston", "Jamaica");
        countryCapitalMap.put("Tokyo", "Japan");
        countryCapitalMap.put("Amman", "Jordan");
        countryCapitalMap.put("Astana", "Kazakhstan");
        countryCapitalMap.put("Nairobi", "Kenya");
        countryCapitalMap.put("Pyongyang", "North Korea");
        countryCapitalMap.put("Seoul", "South Korea");
        countryCapitalMap.put("Pristina", "Kosovo");
        countryCapitalMap.put("Bishkek", "Kyrgyzstan");
        countryCapitalMap.put("Vientiane", "Laos");
        countryCapitalMap.put("Riga", "Latvia");
        countryCapitalMap.put("Beirut", "Lebanon");
        countryCapitalMap.put("Maseru", "Lesotho");
        countryCapitalMap.put("Monrovia", "Liberia");
        countryCapitalMap.put("Vaduz", "Liechtenstein");
        countryCapitalMap.put("Vilnius", "Lithuania");
        countryCapitalMap.put("Luxembourg City", "Luxembourg");
        countryCapitalMap.put("Antananarivo", "Madagascar");
        countryCapitalMap.put("Kuala Lumpur", "Malaysia");
        countryCapitalMap.put("Malé", "Maldives");
        countryCapitalMap.put("Bamako", "Mali");
        countryCapitalMap.put("Valletta", "Malta");
        countryCapitalMap.put("Port Louis", "Mauritius");
        countryCapitalMap.put("Mexico City", "Mexico");
        countryCapitalMap.put("Chișinău", "Moldova");
        countryCapitalMap.put("Ulaanbaatar", "Mongolia");
        countryCapitalMap.put("Podgorica", "Montenegro");
        countryCapitalMap.put("Rabat", "Morocco");
        countryCapitalMap.put("Maputo", "Mozambique");
        countryCapitalMap.put("Naypyidaw", "Myanmar");
        countryCapitalMap.put("Windhoek", "Namibia");
        countryCapitalMap.put("Amsterdam", "Netherlands");
        countryCapitalMap.put("Wellington", "New Zealand");
        countryCapitalMap.put("Managua", "Nicaragua");
        countryCapitalMap.put("Niamey", "Niger");
        countryCapitalMap.put("Abuja", "Nigeria");
        countryCapitalMap.put("Skopje", "North Macedonia");
        countryCapitalMap.put("Belfast", "Northern Ireland");
        countryCapitalMap.put("Oslo", "Norway");
        countryCapitalMap.put("Muscat", "Oman");
        countryCapitalMap.put("Islamabad", "Pakistan");
        countryCapitalMap.put("Panama City", "Panama");
        countryCapitalMap.put("Asunción", "Paraguay");
        countryCapitalMap.put("Lima", "Peru");
        countryCapitalMap.put("Manila", "Philippines");
        countryCapitalMap.put("Warsaw", "Poland");
        countryCapitalMap.put("Lisbon", "Portugal");
        countryCapitalMap.put("Doha", "Qatar");
        countryCapitalMap.put("Bucharest", "Romania");
        countryCapitalMap.put("Moscow", "Russia");
        countryCapitalMap.put("Kigali", "Rwanda");
        countryCapitalMap.put("Apia", "Samoa");
        countryCapitalMap.put("San Marino", "San Marino");
        countryCapitalMap.put("Riyadh", "Saudi Arabia");
        countryCapitalMap.put("Edinburgh", "Scotland");
        countryCapitalMap.put("Dakar", "Senegal");
        countryCapitalMap.put("Belgrade", "Serbia");
        countryCapitalMap.put("Victoria", "Seychelles");
        countryCapitalMap.put("Singapore", "Singapore");
        countryCapitalMap.put("Bratislava", "Slovakia");
        countryCapitalMap.put("Ljubljana", "Slovenia");
        countryCapitalMap.put("Mogadishu", "Somalia");
        countryCapitalMap.put("Madrid", "Spain");
        countryCapitalMap.put("Colombo", "Sri Lanka");
        countryCapitalMap.put("Khartoum", "Sudan");
        countryCapitalMap.put("Paramaribo", "Suriname");
        countryCapitalMap.put("Stockholm", "Sweden");
        countryCapitalMap.put("Damascus", "Syria");
        countryCapitalMap.put("Taipei City", "Taiwan");
        countryCapitalMap.put("Dushanbe", "Tajikistan");
        countryCapitalMap.put("Dodoma", "Tanzania");
        countryCapitalMap.put("Bangkok", "Thailand");
        countryCapitalMap.put("Lomé", "Togo");
        countryCapitalMap.put("Nukualofa", "Tonga");
        countryCapitalMap.put("Tunis", "Tunisia");
        countryCapitalMap.put("Ankara", "Turkey");
        countryCapitalMap.put("Ashgabat", "Turkmenistan");
        countryCapitalMap.put("Kampala", "Uganda");
        countryCapitalMap.put("Kyiv", "Ukraine");
        countryCapitalMap.put("Abu Dhabi", "UAE");
        countryCapitalMap.put("Washington D.C.", "USA");
        countryCapitalMap.put("Montevideo", "Uruguay");
        countryCapitalMap.put("Tashkent", "Uzbekistan");
        countryCapitalMap.put("Port Vila", "Vanuatu");
        countryCapitalMap.put("Caracas", "Venezuela");
        countryCapitalMap.put("Hanoi", "Vietnam");
        countryCapitalMap.put("Cardiff", "Wales");
        countryCapitalMap.put("Sanaa", "Yemen");
        countryCapitalMap.put("Lusaka", "Zambia");
        countryCapitalMap.put("Harare", "Zimbabwe");

        this.countryCapitalMap = countryCapitalMap;

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
            for (int i = 0; i < 4; i++) { // Only enable the first 4 radio buttons for Easy quiz
                radioButtons.get(i).setVisibility(View.VISIBLE);
                radioButtons.get(i).setEnabled(true);
                radioButtons.get(i).setChecked(false);
            }
            for (int i = 4; i < 8; i++) { // Disable the last 4 radio buttons for Easy quiz
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
            // show results , new activity?

            Intent intent = new Intent(CapitalQuiz.this, Results.class);
            intent.putExtra("Difficulty", intentDifficulty);
            intent.putExtra("Category", intentCategory);
            intent.putExtra("Score", score);
            startActivity(intent);

        } else {
            displayCurrentQuestion();
        }
    }

    private String getCountry(String correctCapital) {

        String countryName = countryCapitalMap.get(correctCapital);
        return countryName == null ? "ERROR" : countryName; // Return default value if not found
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