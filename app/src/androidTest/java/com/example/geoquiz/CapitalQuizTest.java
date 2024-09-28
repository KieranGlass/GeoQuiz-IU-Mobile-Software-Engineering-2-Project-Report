package com.example.geoquiz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class CapitalQuizTest {
    private Context appContext;
    private DatabaseHelper mockHelper;
    @Rule
    public ActivityScenarioRule<CapitalQuiz> rule =
            new ActivityScenarioRule<>(CapitalQuiz.class);
    @Before
    public void setUp() {
        appContext = ApplicationProvider.getApplicationContext();
        mockHelper = new DatabaseHelper(appContext, null, null, DatabaseHelper.DB_VERSION);
    }

    @Test //This test ensures database returns all 200 Capitals
    public void checkCorrectAmountOfCapitalsReturned() {
        rule.getScenario().onActivity(activity -> {
            // Arrange
            DatabaseHelperWrapper dbWrapper = new DatabaseHelperWrapper(mockHelper);

            // Act
            List<Country> returnedCountries = dbWrapper.fetchCountries();
            List<String> capitals = new ArrayList<>();

            for (Country country : returnedCountries)
            {
                String capital = country.getCountry_capital();
                capitals.add(capital);
            }
            // Assert
            assertEquals(200, capitals.size());
        });
    }

    @Test //Checks Quiz starts at correct index and contains correct amount of questions
    public void testGenerateEasyQuiz() {
        rule.getScenario().onActivity(activity -> {
            CapitalQuiz capitalQuiz = activity;

            // Assert
            assertNotNull(capitalQuiz.quizQuestions);
            assertEquals(0, capitalQuiz.currentQuestionIndex);
            assertEquals(20, capitalQuiz.quizQuestions.size());
        });
    }
}
