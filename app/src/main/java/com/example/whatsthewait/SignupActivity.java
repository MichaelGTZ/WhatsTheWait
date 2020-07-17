package com.example.whatsthewait;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    public static final String TAG = "Signup Activity";

    // Items on the activity_signup.xml layout
    private EditText etUsername;
    private EditText etPassword;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // If someone is logged in already, don't show login screen and go directly to home screen
        if (ParseUser.getCurrentUser() != null) {
            goToMainActivity();
        }

        // Initialization of each layout item
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Login button clicked");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                signupUser(username, password);
            }
        });
    }

    private void signupUser(final String username, final String password) {
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        // Invoke signUpInBackground
        Log.i(TAG, "SignupUser: Attempting to sign up user: " + username);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    // TODO: Better error handling to let user know the problem
                    Log.e(TAG, "Issue with sign up", e);
                    return;
                } else { // Sign up was successful, now log in the user.
                    loginUser(username, password);
                }
            }
        });
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "loginUser: Attempting to login user: " + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() { // Log in in background on a secondary thread
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    // TODO: Better error handling to let user know the problem
                    Log.e(TAG, "Issue with login", e);
                    return;
                } else {
                    goToMainActivity(); // Should this method just be put into this else???
                }
            }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // This prevents the user from being able to press the back button to go back to login screen after logging in
    }
}