package com.example.geoquiz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;
import android.widget.RadioButton;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(AndroidJUnit4.class)
public class FoodQuizTest {
    private DatabaseHelper mockHelper;
    @Rule
    public ActivityScenarioRule<FoodQuiz> rule =
            new ActivityScenarioRule<>(FoodQuiz.class);
    @Before
    public void setUp() {
        Context appContext = ApplicationProvider.getApplicationContext();
        mockHelper = new DatabaseHelper(appContext, null, null, DatabaseHelper.DB_VERSION);
    }

    @Test //This test ensures database returns all 60 foods
    public void checkCorrectAmountOfFoodsReturned() {
        rule.getScenario().onActivity(activity -> {
            // Arrange
            DatabaseHelperWrapper dbWrapper = new DatabaseHelperWrapper(mockHelper);

            // Act
            List<Food> returnedFoods = dbWrapper.fetchFoods();

            // Assert
            assertEquals(60, returnedFoods.size());
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
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), FoodQuiz.class);
        intent.putExtra("Difficulty", "Medium");

        ActivityScenario<FoodQuiz> scenario = ActivityScenario.launch(intent);

        scenario.onActivity(activity -> {

            assertNotNull(activity.quizQuestions);
            assertEquals(0, activity.currentQuestionIndex);
            assertEquals(20, activity.quizQuestions.size());
            assertEquals("Medium", activity.difficulty);
        });
    }

    @Test //Same as easy check but ensures hard difficulty being used
    public void testGenerateHardQuiz() {
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), FoodQuiz.class);
        intent.putExtra("Difficulty", "Hard");

        ActivityScenario<FoodQuiz> scenario = ActivityScenario.launch(intent);

        scenario.onActivity(activity -> {

            assertNotNull(activity.quizQuestions);
            assertEquals(0, activity.currentQuestionIndex);
            assertEquals(20, activity.quizQuestions.size());
            assertEquals("Hard", activity.difficulty);
        });
    }

    @Test // This tests that a correct answer is handled appropriately.
    public void testOnSubmitClickedCorrectAnswer() {
        rule.getScenario().onActivity(activity -> {

            RadioButton correctButton = null;
            for (RadioButton button : activity.radioButtons) {
                String currentCheckedCountry = button.getText().toString();
                String currentCountry = activity.getFirstQuestionCorrectCountry();
                if (currentCheckedCountry.equals(currentCountry)) {
                    button.setChecked(true);
                    correctButton = button;
                    break;
                }
            }
            // Act
            activity.onSubmitClicked(correctButton);
            // Assert
            assertEquals(1, activity.score);
        });
    }

    @Test // Checks correct amount of radio buttons and that all answers are unique
    public void testUniqueAnswers() {
        ActivityScenario<FoodQuiz> scenario = ActivityScenario.launch(FoodQuiz.class);

        scenario.onActivity(activity -> {

            // Get the RadioGroup containing the answers
            List<RadioButton> radioButtons = activity.radioButtons;

            // Check if there are exactly 6 radio buttons
            assertEquals(6, radioButtons.size());

            // Store all answer texts in a HashSet to check uniqueness
            Set<String> answerTexts = new HashSet<>();

            for (int i = 0; i < radioButtons.size(); i++) {
                RadioButton radioButton = radioButtons.get(i);
                String answerText = radioButton.getText().toString();

                // Check if the answer is unique
                assertTrue(answerTexts.add(answerText));
            }
        });
    }
}
