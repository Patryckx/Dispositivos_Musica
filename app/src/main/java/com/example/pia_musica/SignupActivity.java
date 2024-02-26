package com.example.pia_musica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    EditText signupName, signupUsername, signupEmail, signupPassword, signupConfirmPassword;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize EditText fields
        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        signupConfirmPassword = findViewById(R.id.signup_confirm_password);

        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);

        // Set onClickListener for signupButton
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get text from EditText fields
                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String username = signupUsername.getText().toString();
                String password = signupPassword.getText().toString();
                String confirmPassword = signupConfirmPassword.getText().toString();

                // Check if passwords match
                if (!password.equals(confirmPassword)) {
                    // Show error message if passwords don't match
                    Toast.makeText(SignupActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // If passwords match, proceed with user creation
                createUser(name, email, username, password);
            }
        });

        // Set onClickListener for loginRedirectText
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open LoginActivity when loginRedirectText is clicked
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createUser(String name, String email, String username, String password) {
        // Initialize Firebase database reference
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        // Create a new HelperClass object with user data
        HelperClass helperClass = new HelperClass(name, email, username, password);

        // Set the user data in the Firebase database
        reference.child(username).setValue(helperClass);

        // Show success message
        Toast.makeText(SignupActivity.this, "You have signed up successfully!", Toast.LENGTH_SHORT).show();

        // Open LoginActivity
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
