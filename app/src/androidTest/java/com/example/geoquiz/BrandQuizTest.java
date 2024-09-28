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
public class BrandQuizTest {
    private Context appContext;
    private DatabaseHelper mockHelper;
    @Rule
    public ActivityScenarioRule<BrandQuiz> rule =
            new ActivityScenarioRule<>(BrandQuiz.class);
    @Before
    public void setUp() {
        appContext = ApplicationProvider.getApplicationContext();
        mockHelper = new DatabaseHelper(appContext, null, null, DatabaseHelper.DB_VERSION);
    }

    @Test //This test ensures database returns all 60 brands
    public void checkCorrectAmountOfBrandsReturned() {
        rule.getScenario().onActivity(activity -> {
            // Arrange
            DatabaseHelperWrapper dbWrapper = new DatabaseHelperWrapper(mockHelper);

            // Act
            List<Brand> returnedBrands = dbWrapper.fetchBrands();

            // Assert
            assertEquals(60, returnedBrands.size());
        });
    }

    @Test //Checks Quiz starts at correct index and contains correct amount of questions
    public void testGenerateEasyQuiz() {
        rule.getScenario().onActivity(activity -> {
            BrandQuiz brandQuiz = activity;
            // Assert
            assertNotNull(brandQuiz.quizQuestions);
            assertEquals(0, brandQuiz.currentQuestionIndex);
            assertEquals(20, brandQuiz.quizQuestions.size());
        });
    }
}
