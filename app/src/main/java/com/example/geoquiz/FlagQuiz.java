package com.example.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

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

public class FlagQuiz extends AppCompatActivity {

    private ImageView ivFlagQuestion;
    private List<RadioButton> radioButtons;
    private Button submitBtn;

    private String[] easyCountriesArray;
    private String[] mediumCountriesArray;
    private String[] hardCountriesArray;
    private HashMap<String, Integer> countryFlagMap;

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

        initCountryFlagMap();

        easyCountriesArray = getResources().getStringArray(R.array.easyCountries);
        mediumCountriesArray = getResources().getStringArray(R.array.mediumCountries);
        hardCountriesArray = getResources().getStringArray(R.array.hardCountries);

        ivFlagQuestion = findViewById(R.id.ivFlagQuestion);
        radioButtons = new ArrayList<>();
        Collections.addAll(radioButtons,
                findViewById(R.id.rb1Flag),
                findViewById(R.id.rb2Flag),
                findViewById(R.id.rb3Flag),
                findViewById(R.id.rb4Flag));

        submitBtn = findViewById(R.id.submitBtn);

        Intent intent = getIntent();
        String difficulty = intent.getStringExtra("Difficulty");

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

    private void generateEasyQuiz() {
        Random random = new Random();
        int correctCountryIndex = random.nextInt(easyCountriesArray.length); //
        String correctCountry = easyCountriesArray[correctCountryIndex];
        ivFlagQuestion.setImageResource(getFlagResource(correctCountry));

        List<String> wrongAnswers = new ArrayList<>();
        while (wrongAnswers.size() < 3) {
            int wrongCountryIndex = random.nextInt(easyCountriesArray.length);
            if (wrongCountryIndex != correctCountryIndex && !wrongAnswers.contains(easyCountriesArray[wrongCountryIndex])) {
                wrongAnswers.add(easyCountriesArray[wrongCountryIndex]);
            }
        }

        List<String> allAnswers = new ArrayList<>();
        allAnswers.add(correctCountry);
        allAnswers.addAll(wrongAnswers);
        Collections.shuffle(allAnswers, random);

        for (int i = 0; i < radioButtons.size(); i++) {
            radioButtons.get(i).setText(allAnswers.get(i));
        }
    }

    private void generateMediumQuiz() {
        Random random = new Random();
        int correctCountryIndex = random.nextInt(mediumCountriesArray.length); //
        String correctCountry = mediumCountriesArray[correctCountryIndex];
        ivFlagQuestion.setImageResource(getFlagResource(correctCountry));

        List<String> wrongAnswers = new ArrayList<>();
        while (wrongAnswers.size() < 3) {
            int wrongCountryIndex = random.nextInt(mediumCountriesArray.length);
            if (wrongCountryIndex != correctCountryIndex && !wrongAnswers.contains(mediumCountriesArray[wrongCountryIndex])) {
                wrongAnswers.add(mediumCountriesArray[wrongCountryIndex]);
            }
        }

        List<String> allAnswers = new ArrayList<>();
        allAnswers.add(correctCountry);
        allAnswers.addAll(wrongAnswers);
        Collections.shuffle(allAnswers, random);

        for (int i = 0; i < radioButtons.size(); i++) {
            radioButtons.get(i).setText(allAnswers.get(i));
        }
    }

    private void generateHardQuiz() {
        Random random = new Random();
        int correctCountryIndex = random.nextInt(hardCountriesArray.length); //
        String correctCountry = hardCountriesArray[correctCountryIndex];
        ivFlagQuestion.setImageResource(getFlagResource(correctCountry));

        List<String> wrongAnswers = new ArrayList<>();
        while (wrongAnswers.size() < 3) {
            int wrongCountryIndex = random.nextInt(hardCountriesArray.length);
            if (wrongCountryIndex != correctCountryIndex && !wrongAnswers.contains(hardCountriesArray[wrongCountryIndex])) {
                wrongAnswers.add(hardCountriesArray[wrongCountryIndex]);
            }
        }

        List<String> allAnswers = new ArrayList<>();
        allAnswers.add(correctCountry);
        allAnswers.addAll(wrongAnswers);
        Collections.shuffle(allAnswers, random);

        for (int i = 0; i < radioButtons.size(); i++) {
            radioButtons.get(i).setText(allAnswers.get(i));
        }
    }

    private void initCountryFlagMap() {
        HashMap<String, Integer> countryFlagMap = new HashMap<>();

        countryFlagMap.put("Afghanistan", R.drawable.afghanistan_flag);
        countryFlagMap.put("Albania", R.drawable.albania_flag);
        countryFlagMap.put("Algeria", R.drawable.algeria_flag);
        countryFlagMap.put("Angola", R.drawable.angola_flag);
        countryFlagMap.put("Argentina", R.drawable.argentina_flag);
        countryFlagMap.put("Australia", R.drawable.australia_flag);
        countryFlagMap.put("Austria", R.drawable.austria_flag);
        countryFlagMap.put("Azerbaijan", R.drawable.azerbaijan_flag);
        countryFlagMap.put("Bahrain", R.drawable.bahrain_flag);
        countryFlagMap.put("Bangladesh", R.drawable.bangladesh_flag);
        countryFlagMap.put("Barbados", R.drawable.barbados_flag);
        countryFlagMap.put("Belarus", R.drawable.belarus_flag);
        countryFlagMap.put("Belgium", R.drawable.belgium_flag);
        countryFlagMap.put("Belize", R.drawable.belize_flag);
        countryFlagMap.put("Bhutan", R.drawable.bhutan_flag);
        countryFlagMap.put("Bolivia", R.drawable.bolivia_flag);
        countryFlagMap.put("Botswana", R.drawable.botswana_flag);
        countryFlagMap.put("Brazil", R.drawable.brazil_flag);
        countryFlagMap.put("Bulgaria", R.drawable.bulgaria_flag);
        countryFlagMap.put("Burkina Faso", R.drawable.burkina_faso_flag);
        countryFlagMap.put("Burundi", R.drawable.burundi_flag);
        countryFlagMap.put("Cape Verde", R.drawable.cape_verde_flag);
        countryFlagMap.put("Cambodia", R.drawable.cambodia_flag);
        countryFlagMap.put("Cameroon", R.drawable.cameroon_flag);
        countryFlagMap.put("Canada", R.drawable.canada_flag);
        countryFlagMap.put("Chile", R.drawable.chile_flag);
        countryFlagMap.put("China", R.drawable.china_flag);
        countryFlagMap.put("Colombia", R.drawable.colombia_flag);
        countryFlagMap.put("Comoros", R.drawable.comoros_flag);
        countryFlagMap.put("DR Congo", R.drawable.dr_congo_flag);
        countryFlagMap.put("Congo", R.drawable.congo_flag);
        countryFlagMap.put("Costa Rica", R.drawable.costa_rica_flag);
        countryFlagMap.put("Ivory Coast", R.drawable.ivory_coast_flag);
        countryFlagMap.put("Croatia", R.drawable.croatia_flag);
        countryFlagMap.put("Cuba", R.drawable.cuba_flag);
        countryFlagMap.put("Cyprus", R.drawable.cyprus_flag);
        countryFlagMap.put("Czechia", R.drawable.czechia_flag);
        countryFlagMap.put("Denmark", R.drawable.denmark_flag);
        countryFlagMap.put("Djibouti", R.drawable.djibouti_flag);
        countryFlagMap.put("Dominica", R.drawable.dominica_flag);
        countryFlagMap.put("Dominican Republic", R.drawable.dominican_republic_flag);
        countryFlagMap.put("Ecuador", R.drawable.ecuador_flag);
        countryFlagMap.put("Egypt", R.drawable.egypt_flag);
        countryFlagMap.put("England", R.drawable.england_flag);
        countryFlagMap.put("Eritrea", R.drawable.eritrea_flag);
        countryFlagMap.put("Estonia", R.drawable.estonia_flag);
        countryFlagMap.put("Ethiopia", R.drawable.ethiopia_flag);
        countryFlagMap.put("Fiji", R.drawable.fiji_flag);
        countryFlagMap.put("Finland", R.drawable.finland_flag);
        countryFlagMap.put("France", R.drawable.france_flag);
        countryFlagMap.put("Gambia", R.drawable.gambia_flag);
        countryFlagMap.put("Georgia", R.drawable.georgia_flag);
        countryFlagMap.put("Germany", R.drawable.germany_flag);
        countryFlagMap.put("Ghana", R.drawable.ghana_flag);
        countryFlagMap.put("Greece", R.drawable.greece_flag);
        countryFlagMap.put("Guatemala", R.drawable.guatemala_flag);
        countryFlagMap.put("Guinea", R.drawable.guinea_flag);
        countryFlagMap.put("Guinea-Bissau", R.drawable.guinea_bissau_flag);
        countryFlagMap.put("Guyana", R.drawable.guyana_flag);
        countryFlagMap.put("Haiti", R.drawable.haiti_flag);
        countryFlagMap.put("Honduras", R.drawable.honduras_flag);
        countryFlagMap.put("Hungary", R.drawable.hungary_flag);
        countryFlagMap.put("Iceland", R.drawable.iceland_flag);
        countryFlagMap.put("India", R.drawable.india_flag);
        countryFlagMap.put("Indonesia", R.drawable.indonesia_flag);
        countryFlagMap.put("Iran", R.drawable.iran_flag);
        countryFlagMap.put("Iraq", R.drawable.iraq_flag);
        countryFlagMap.put("Ireland", R.drawable.ireland_flag);
        countryFlagMap.put("Israel", R.drawable.israel_flag);
        countryFlagMap.put("Italy", R.drawable.italy_flag);
        countryFlagMap.put("Jamaica", R.drawable.jamaica_flag);
        countryFlagMap.put("Japan", R.drawable.japan_flag);
        countryFlagMap.put("Jordan", R.drawable.jordan_flag);
        countryFlagMap.put("Kazakhstan", R.drawable.kazakhstan_flag);
        countryFlagMap.put("Kenya", R.drawable.kenya_flag);
        countryFlagMap.put("North Korea", R.drawable.north_korea_flag);
        countryFlagMap.put("South Korea", R.drawable.south_korea_flag);
        countryFlagMap.put("Kosovo", R.drawable.kosovo_flag);
        countryFlagMap.put("Kyrgyzstan", R.drawable.kyrgyzstan_flag);
        countryFlagMap.put("Laos", R.drawable.laos_flag);
        countryFlagMap.put("Latvia", R.drawable.latvia_flag);
        countryFlagMap.put("Lebanon", R.drawable.lebanon_flag);
        countryFlagMap.put("Lesotho", R.drawable.lesotho_flag);
        countryFlagMap.put("Liberia", R.drawable.liberia_flag);
        countryFlagMap.put("Liechtenstein", R.drawable.liechtenstein_flag);
        countryFlagMap.put("Lithuania", R.drawable.lithuania_flag);
        countryFlagMap.put("Luxembourg", R.drawable.luxembourg_flag);
        countryFlagMap.put("Madagascar", R.drawable.madagascar_flag);
        countryFlagMap.put("Malaysia", R.drawable.malaysia_flag);
        countryFlagMap.put("Maldives", R.drawable.maldives_flag);
        countryFlagMap.put("Mali", R.drawable.mali_flag);
        countryFlagMap.put("Malta", R.drawable.malta_flag);
        countryFlagMap.put("Mauritius", R.drawable.mauritius_flag);
        countryFlagMap.put("Mexico", R.drawable.mexico_flag);
        countryFlagMap.put("Moldova", R.drawable.moldova_flag);
        countryFlagMap.put("Mongolia", R.drawable.mongolia_flag);
        countryFlagMap.put("Montenegro", R.drawable.montenegro_flag);
        countryFlagMap.put("Morocco", R.drawable.morocco_flag);
        countryFlagMap.put("Mozambique", R.drawable.mozambique_flag);
        countryFlagMap.put("Myanmar", R.drawable.myanmar_flag);
        countryFlagMap.put("Namibia", R.drawable.namibia_flag);
        countryFlagMap.put("Netherlands", R.drawable.netherlands_flag);
        countryFlagMap.put("New Zealand", R.drawable.new_zealand_flag);
        countryFlagMap.put("Nicaragua", R.drawable.nicaragua_flag);
        countryFlagMap.put("Niger", R.drawable.niger_flag);
        countryFlagMap.put("Nigeria", R.drawable.nigeria_flag);
        countryFlagMap.put("North Macedonia", R.drawable.north_macedonia_flag);
        countryFlagMap.put("Northern Ireland", R.drawable.northern_ireland_flag);
        countryFlagMap.put("Norway", R.drawable.norway_flag);
        countryFlagMap.put("Oman", R.drawable.oman_flag);
        countryFlagMap.put("Pakistan", R.drawable.pakistan_flag);
        countryFlagMap.put("Panama", R.drawable.panama_flag);
        countryFlagMap.put("Papua New Guinea", R.drawable.papua_new_guinea_flag);
        countryFlagMap.put("Paraguay", R.drawable.paraguay_flag);
        countryFlagMap.put("Peru", R.drawable.peru_flag);
        countryFlagMap.put("Philippines", R.drawable.philippines_flag);
        countryFlagMap.put("Poland", R.drawable.poland_flag);
        countryFlagMap.put("Portugal", R.drawable.portugal_flag);
        countryFlagMap.put("Qatar", R.drawable.qatar_flag);
        countryFlagMap.put("Romania", R.drawable.romania_flag);
        countryFlagMap.put("Russia", R.drawable.russia_flag);
        countryFlagMap.put("Rwanda", R.drawable.rwanda_flag);
        countryFlagMap.put("Samoa", R.drawable.samoa_flag);
        countryFlagMap.put("San Marino", R.drawable.san_marino_flag);
        countryFlagMap.put("Saudi Arabia", R.drawable.saudi_arabia_flag);
        countryFlagMap.put("Scotland", R.drawable.scotland_flag);
        countryFlagMap.put("Senegal", R.drawable.senegal_flag);
        countryFlagMap.put("Serbia", R.drawable.serbia_flag);
        countryFlagMap.put("Seychelles", R.drawable.seychelles_flag);
        countryFlagMap.put("Singapore", R.drawable.singapore_flag);
        countryFlagMap.put("Slovakia", R.drawable.slovakia_flag);
        countryFlagMap.put("Slovenia", R.drawable.slovenia_flag);
        countryFlagMap.put("Somalia", R.drawable.somalia_flag);
        countryFlagMap.put("South Africa", R.drawable.south_africa_flag);
        countryFlagMap.put("Spain", R.drawable.spain_flag);
        countryFlagMap.put("Sri Lanka", R.drawable.sri_lanka_flag);
        countryFlagMap.put("Sudan", R.drawable.sudan_flag);
        countryFlagMap.put("Suriname", R.drawable.suriname_flag);
        countryFlagMap.put("Sweden", R.drawable.sweden_flag);
        countryFlagMap.put("Syria", R.drawable.syria_flag);
        countryFlagMap.put("Taiwan", R.drawable.taiwan_flag);
        countryFlagMap.put("Tajikistan", R.drawable.tajikistan_flag);
        countryFlagMap.put("Tanzania", R.drawable.tanzania_flag);
        countryFlagMap.put("Thailand", R.drawable.thailand_flag);
        countryFlagMap.put("Togo", R.drawable.togo_flag);
        countryFlagMap.put("Tonga", R.drawable.tonga_flag);
        countryFlagMap.put("Tunisia", R.drawable.tunisia_flag);
        countryFlagMap.put("Turkey", R.drawable.turkey_flag);
        countryFlagMap.put("Turkmenistan", R.drawable.turkmenistan_flag);
        countryFlagMap.put("Uganda", R.drawable.uganda_flag);
        countryFlagMap.put("Ukraine", R.drawable.ukraine_flag);
        countryFlagMap.put("UAE", R.drawable.uae_flag);
        countryFlagMap.put("USA", R.drawable.usa_flag);
        countryFlagMap.put("Uruguay", R.drawable.uruguay_flag);
        countryFlagMap.put("Uzbekistan", R.drawable.uzbekistan_flag);
        countryFlagMap.put("Vanuatu", R.drawable.vanuatu_flag);
        countryFlagMap.put("Venezuela", R.drawable.venezuela_flag);
        countryFlagMap.put("Vietnam", R.drawable.vietnam_flag);
        countryFlagMap.put("Wales", R.drawable.wales_flag);
        countryFlagMap.put("Yemen", R.drawable.yemen_flag);
        countryFlagMap.put("Zambia", R.drawable.zambia_flag);
        countryFlagMap.put("Zimbabwe", R.drawable.zimbabwe_flag);

        this.countryFlagMap = countryFlagMap;
    }

    private int getFlagResource(String countryName) {

        Integer resourceId = countryFlagMap.get(countryName);
        return resourceId == null ? 0 : resourceId; // Return default value if not found
    }
}