package com.example.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    //TODO Need to design home screen along with organising usernames + logins

    //TODO Figure out how to organise database for logins
    //TODO Figure out how to load previously saved best scores
    Button startBtn, joinBtn;
    RadioButton rbLogin, rbSignup;
    EditText loginUsername, loginPassword, signupUsername, signupPassword;
    private RadioButton lastCheckedRadioButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startBtn = findViewById(R.id.enterBtn);
        joinBtn = findViewById(R.id.joinBtn);
        rbLogin = findViewById(R.id.rbTitle1);
        rbSignup = findViewById(R.id.rbTitle2);

        loginUsername = findViewById(R.id.loginUsername);
        loginPassword = findViewById(R.id.loginPassword);
        signupUsername = findViewById(R.id.signupUsername);
        signupPassword = findViewById(R.id.signupPassword);

        lastCheckedRadioButton = rbLogin;

        CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
            if (isChecked) {
                // Uncheck the previously checked RadioButton
                if (lastCheckedRadioButton != null && lastCheckedRadioButton.getId() != buttonView.getId()) {
                    lastCheckedRadioButton.setChecked(false);
                }

                // Update the lastCheckedRadioButton variable
                lastCheckedRadioButton = (RadioButton) buttonView;

                // Enable/disable buttons based on the selected RadioButton
                if (buttonView == rbLogin) {
                    signupUsername.setEnabled(false);
                    signupPassword.setEnabled(false);
                    joinBtn.setEnabled(false);
                    loginUsername.setEnabled(true);
                    loginPassword.setEnabled(true);
                    startBtn.setEnabled(true);
                } else if (buttonView == rbSignup) {
                    loginUsername.setEnabled(false);
                    loginPassword.setEnabled(false);
                    startBtn.setEnabled(false);
                    signupUsername.setEnabled(true);
                    signupPassword.setEnabled(true);
                    joinBtn.setEnabled(true);
                }
            }
        };

        rbLogin.setOnCheckedChangeListener(listener);
        rbSignup.setOnCheckedChangeListener(listener);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuizDashboard.class);
                startActivity(intent);
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = signupUsername.getText().toString();
                String password = signupPassword.getText().toString();

                if (!username.isEmpty() && !password.isEmpty()) {
                    DatabaseHelper db = new DatabaseHelper(MainActivity.this, null, null, DatabaseHelper.DB_VERSION);
                    db.createUser(username, password);
                } else {
                    Toast.makeText(MainActivity.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}