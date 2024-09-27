package com.example.geoquiz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class FlagQuizTest {
    private Context appContext;
    private DatabaseHelper mockHelper;
    @Rule
    public ActivityScenarioRule<FlagQuiz> rule =
            new ActivityScenarioRule<>(FlagQuiz.class);
    @Before
    public void setUp() {
        appContext = ApplicationProvider.getApplicationContext();
        mockHelper = new DatabaseHelper(appContext, null, null, DatabaseHelper.DB_VERSION);
    }
    @Test //This test ensures database returns all 200 countries
    public void checkCorrectAmountOfCountriesReturned() {
        rule.getScenario().onActivity(activity -> {
            // Arrange
            DatabaseHelperWrapper dbWrapper = new DatabaseHelperWrapper(mockHelper);

            // Act
            List<Country> returnedCountries = dbWrapper.fetchCountries();

            // Assert
            assertEquals(200, returnedCountries.size());
        });
    }
    @Test //This test tests basic generation of easy quiz and also that i accesses all 40 'easy' countries
    public void testGenerateEasyQuiz() {
        rule.getScenario().onActivity(activity -> {
            FlagQuiz flagQuiz = activity;

            // Act
            flagQuiz.generateEasyQuiz();

            // Assert
            assertNotNull(flagQuiz.quizQuestions);
            assertEquals(0, flagQuiz.currentQuestionIndex);
            assertEquals(40, flagQuiz.quizQuestions.size());
        });
    }

/*
    @Test
    public void testDisplayCurrentQuestion() {
        rule.getScenario().onActivity(activity -> {
            FlagQuiz flagQuiz = activity;

            // Arrange
            QuizQuestion quizQuestion = new QuizQuestion();
            quizQuestion.correctCountry = "England";
            quizQuestion.difficultyLevel = "easy";

            List<QuizQuestion> quizQuestions = Collections.singletonList(quizQuestion);
            flagQuiz.quizQuestions = quizQuestions;

            // Act
            flagQuiz.displayCurrentQuestion();

            // Assert
            View view = activity.findViewById(R.id.ivFlagQuestion);
            assertNotNull(view);

            TextView tvCounter = activity.findViewById(R.id.tvCounter);
            assertEquals("1", tvCounter.getText().toString());

            RadioButton rbFlag1 = activity.findViewById(R.id.rb1Flag);
            assertEquals("England", rbFlag1.getText().toString());

            for (int i = 2; i <= 6; i++) {
                RadioButton rbFlag = activity.findViewById(getResources().getIdentifier("rbFlag" + i, "id", getPackageName()));
                assertEquals("", rbFlag.getText().toString());
            }
        });
    } */

    @Test // This tests that a correct answer is handled appropriately.
    public void testOnSubmitClickedCorrectAnswer() {
        rule.getScenario().onActivity(activity -> {
            FlagQuiz flagQuiz = activity;

            RadioButton correctButton = null;
            for (RadioButton button : flagQuiz.radioButtons) {
                String currentCheckedCountry = button.getText().toString();
                String currentCountry = flagQuiz.getFirstQuestionCorrectCountry();
                if (currentCheckedCountry.equals(currentCountry)) {
                    button.setChecked(true);
                    correctButton = button;
                    break;
                }
            }
            // Act
            flagQuiz.onSubmitClicked(correctButton);
            // Assert
            assertEquals(1, flagQuiz.score);
        });
    }

    @Test
    public void testOnSubmitClickedIncorrectAnswer() {
        rule.getScenario().onActivity(activity -> {
            FlagQuiz flagQuiz = activity;

            RadioButton correctButton = null;
            for (RadioButton button : flagQuiz.radioButtons) {
                String currentCheckedCountry = button.getText().toString();
                String currentCountry = flagQuiz.getFirstQuestionCorrectCountry();
                if (currentCheckedCountry.equals(currentCountry)) {
                    button.setChecked(false);
                    correctButton = button;
                    break;
                }
            }
            // Act
            flagQuiz.onSubmitClicked(correctButton);
            // Assert
            assertEquals(0, flagQuiz.score);
        });
    }
}
