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

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class SportsQuizTest {
    private Context appContext;
    private DatabaseHelper mockHelper;
    @Rule
    public ActivityScenarioRule<SportsQuiz> rule =
            new ActivityScenarioRule<>(SportsQuiz.class);
    @Before
    public void setUp() {
        appContext = ApplicationProvider.getApplicationContext();
        mockHelper = new DatabaseHelper(appContext, null, null, DatabaseHelper.DB_VERSION);
    }

    @Test //This test ensures database returns all 60 Sports Teams
    public void checkCorrectAmountOfSportsTeamsReturned() {
        rule.getScenario().onActivity(activity -> {
            // Arrange
            DatabaseHelperWrapper dbWrapper = new DatabaseHelperWrapper(mockHelper);

            // Act
            List<Sports> returnedSportsTeams = dbWrapper.fetchSports();

            // Assert
            assertEquals(60, returnedSportsTeams.size());
        });
    }

    @Test //Checks Quiz starts at correct index and contains correct amount of questions
    public void testGenerateEasyQuiz() {
        rule.getScenario().onActivity(activity -> {
            SportsQuiz sportsQuiz = activity;

            // Assert
            assertNotNull(sportsQuiz.quizQuestions);
            assertEquals(0, sportsQuiz.currentQuestionIndex);
            assertEquals(20, sportsQuiz.quizQuestions.size());
        });
    }
}
