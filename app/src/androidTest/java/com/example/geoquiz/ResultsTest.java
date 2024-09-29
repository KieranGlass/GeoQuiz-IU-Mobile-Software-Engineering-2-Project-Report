package com.example.geoquiz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import android.content.Intent;

import androidx.test.core.app.ActivityScenario;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ResultsTest {

    @Rule
    public ActivityScenarioRule<Results> rule = new ActivityScenarioRule<>(Results.class);

    @Before
    public void setUp() {

    }

    @Test //Checks intents received are properly update to results page
    public void testResultsDisplayedCorrectly() {
        Intent intent1 = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), Results.class);
        intent1.putExtra("Difficulty", "Easy");
        intent1.putExtra("Category", "Flag");
        intent1.putExtra("Score", 10);

        ActivityScenario<Results> scenario1 = ActivityScenario.launch(intent1);

        scenario1.onActivity(activity -> {

            assertNotNull(activity.categoryChosen);
            assertNotNull(activity.difficultyChosen);
            assertNotNull(activity.scoreObtained);
            assertNotNull(activity.ivResults);

            assertEquals("Flag", activity.categoryChosen.getText());
            assertEquals("Easy", activity.difficultyChosen.getText());
            assertEquals("50 %", activity.scoreObtained.getText());

        });

        Intent intent2 = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), Results.class);
        intent2.putExtra("Difficulty", "Hard");
        intent2.putExtra("Category", "Capital");
        intent2.putExtra("Score", 5);

        ActivityScenario<Results> scenario2 = ActivityScenario.launch(intent2);

        scenario2.onActivity(activity -> {

            assertNotNull(activity.categoryChosen);
            assertNotNull(activity.difficultyChosen);
            assertNotNull(activity.scoreObtained);
            assertNotNull(activity.ivResults);

            assertEquals("Capital", activity.categoryChosen.getText());
            assertEquals("Hard", activity.difficultyChosen.getText());
            assertEquals("25 %", activity.scoreObtained.getText());

        });
    }
}
