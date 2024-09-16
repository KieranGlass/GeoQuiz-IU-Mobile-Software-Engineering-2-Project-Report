package com.example.geoquiz;

import android.content.Intent;
import android.widget.RadioButton;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import com.example.geoquiz.DatabaseHelper;
import com.example.geoquiz.FlagQuiz;

import junit.framework.TestCase;

public class FlagQuizTest extends TestCase {

    public void testRadioButtonSelection() {
        FlagQuiz quiz = new FlagQuiz();
        quiz.radioButtons.add(new RadioButton(null));
        quiz.radioButtons.add(new RadioButton(null));

        quiz.lastCheckedRadioButton = null;

        quiz.radioButtons.get(0).setChecked(true);

        assertEquals(quiz.lastCheckedRadioButton, quiz.radioButtons.get(0));

        quiz.radioButtons.get(1).setChecked(true);

        assertEquals(quiz.lastCheckedRadioButton, quiz.radioButtons.get(1));
    }
}
