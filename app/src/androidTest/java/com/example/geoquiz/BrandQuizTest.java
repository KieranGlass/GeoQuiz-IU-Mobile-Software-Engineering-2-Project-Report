package com.example.geoquiz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class BrandQuizTest {
    private DatabaseHelper mockHelper;
    @Rule
    public ActivityScenarioRule<BrandQuiz> rule =
            new ActivityScenarioRule<>(BrandQuiz.class);
    @Before
    public void setUp() {
        Context appContext = ApplicationProvider.getApplicationContext();
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
            // Assert
            assertNotNull(activity.quizQuestions);
            assertEquals(0, activity.currentQuestionIndex);
            assertEquals(20, activity.quizQuestions.size());
        });
    }

    @Test //Same as easy check but ensures medium difficulty being used
    public void testGenerateMediumQuiz() {
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), BrandQuiz.class);
        intent.putExtra("Difficulty", "Medium");

        ActivityScenario<BrandQuiz> scenario = ActivityScenario.launch(intent);

        scenario.onActivity(activity -> {

            assertNotNull(activity.quizQuestions);
            assertEquals(0, activity.currentQuestionIndex);
            assertEquals(20, activity.quizQuestions.size());
            assertEquals("Medium", activity.difficulty);
        });
    }

    @Test //Same as easy check but ensures hard difficulty being used
    public void testGenerateHardQuiz() {
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), BrandQuiz.class);
        intent.putExtra("Difficulty", "Hard");

        ActivityScenario<BrandQuiz> scenario = ActivityScenario.launch(intent);

        scenario.onActivity(activity -> {

            assertNotNull(activity.quizQuestions);
            assertEquals(0, activity.currentQuestionIndex);
            assertEquals(20, activity.quizQuestions.size());
            assertEquals("Hard", activity.difficulty);
        });
    }
}
