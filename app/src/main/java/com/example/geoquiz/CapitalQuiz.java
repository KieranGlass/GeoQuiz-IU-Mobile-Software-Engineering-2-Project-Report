package com.example.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class CapitalQuiz extends AppCompatActivity {

    private TextView tvCapital;
    private String[] capitalsArray, countriesArray;
    private HashMap<String, String> countryCapitalMap;
    private RadioButton[] radioButtons;

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
        capitalsArray = getResources().getStringArray(R.array.capitals);
        countriesArray = getResources().getStringArray(R.array.countries);
        radioButtons = new RadioButton[]
                {
                        findViewById(R.id.rb1Capital),
                        findViewById(R.id.rb2Capital),
                        findViewById(R.id.rb3Capital),
                        findViewById(R.id.rb4Capital),
                        findViewById(R.id.rb5Capital),
                        findViewById(R.id.rb6Capital),
                        findViewById(R.id.rb7Capital),
                        findViewById(R.id.rb8Capital)
                };

        radioButtons[4].setVisibility(View.GONE);
        radioButtons[5].setVisibility(View.GONE);
        radioButtons[6].setVisibility(View.GONE);
        radioButtons[7].setVisibility(View.GONE);

        Intent intent = getIntent();
        String difficulty = intent.getStringExtra("Difficulty");

        setUpRadioButtons(difficulty);

        if (difficulty.equals("Easy"))
        {
            generateEasyQuiz();
        }
        else if (difficulty.equals("Medium"))
        {
            generateMediumQuiz();
        }
        else
        {
            generateHardQuiz();
        }
    }



    private void setUpRadioButtons (String difficulty)
    {

        if (difficulty.equals("Medium"))
        {
            radioButtons[4].setVisibility(View.VISIBLE);
            radioButtons[5].setVisibility(View.VISIBLE);
        }
        else if (difficulty.equals("Hard"))
        {
            radioButtons[4].setVisibility(View.VISIBLE);
            radioButtons[5].setVisibility(View.VISIBLE);
            radioButtons[6].setVisibility(View.VISIBLE);
            radioButtons[7].setVisibility(View.VISIBLE);
        }


    }
    private void generateEasyQuiz() {


        Random random = new Random();
        int correctCapitalIndex = random.nextInt(capitalsArray.length); //
        String correctCapital = capitalsArray[correctCapitalIndex];
        tvCapital.setText(correctCapital);

        List<String> wrongAnswers = new ArrayList<>();
        while (wrongAnswers.size() < 3) {
            int wrongCountryIndex = random.nextInt(countriesArray.length);
            if (wrongCountryIndex != correctCapitalIndex && !wrongAnswers.contains(countriesArray[wrongCountryIndex])) {
                wrongAnswers.add(countriesArray[wrongCountryIndex]);
            }
        }

        List<String> allAnswers = new ArrayList<>();
        allAnswers.add(getCountry(correctCapital));
        allAnswers.addAll(wrongAnswers);
        Collections.shuffle(allAnswers, random);

        for (int i = 0; i < 4; i++) {
            radioButtons[i].setText(allAnswers.get(i));
        }

    }


    private void generateMediumQuiz()
    {
        Random random = new Random();
        int correctCapitalIndex = random.nextInt(capitalsArray.length); //
        String correctCapital = capitalsArray[correctCapitalIndex];
        tvCapital.setText(correctCapital);

        List<String> wrongAnswers = new ArrayList<>();
        while (wrongAnswers.size() < 5) {
            int wrongCountryIndex = random.nextInt(countriesArray.length);
            if (wrongCountryIndex != correctCapitalIndex && !wrongAnswers.contains(countriesArray[wrongCountryIndex])) {
                wrongAnswers.add(countriesArray[wrongCountryIndex]);
            }
        }

        List<String> allAnswers = new ArrayList<>();
        allAnswers.add(getCountry(correctCapital));
        allAnswers.addAll(wrongAnswers);
        Collections.shuffle(allAnswers, random);

        for (int i = 0; i < 6; i++) {
            radioButtons[i].setText(allAnswers.get(i));
        }
    }

    private void generateHardQuiz()
    {
        Random random = new Random();
        int correctCapitalIndex = random.nextInt(capitalsArray.length); //
        String correctCapital = capitalsArray[correctCapitalIndex];
        tvCapital.setText(correctCapital);

        List<String> wrongAnswers = new ArrayList<>();
        while (wrongAnswers.size() < 7) {
            int wrongCountryIndex = random.nextInt(countriesArray.length);
            if (wrongCountryIndex != correctCapitalIndex && !wrongAnswers.contains(countriesArray[wrongCountryIndex])) {
                wrongAnswers.add(countriesArray[wrongCountryIndex]);
            }
        }

        List<String> allAnswers = new ArrayList<>();
        allAnswers.add(getCountry(correctCapital));
        allAnswers.addAll(wrongAnswers);
        Collections.shuffle(allAnswers, random);

        for (int i = 0; i < 8; i++) {
            radioButtons[i].setText(allAnswers.get(i));
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
        countryCapitalMap.put("Brasília", "Brazil");
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
        countryCapitalMap.put("Santo Domingo", "Dominican Republic");
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
        countryCapitalMap.put("Port Moresby", "Papua New Guinea");
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
        countryCapitalMap.put("Sana'a", "Yemen");
        countryCapitalMap.put("Lusaka", "Zambia");
        countryCapitalMap.put("Harare", "Zimbabwe");

        this.countryCapitalMap = countryCapitalMap;

    }

    private String getCountry(String correctCapital) {

        String countryName = countryCapitalMap.get(correctCapital);
        return countryName == null ? "-" : countryName; // Return default value if not found
    }
}