package com.example.pia_musica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    private void createUser(final String name, final String email, final String username, final String password) {
        // Inicializa la instancia de FirebaseAuth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // Crea el usuario utilizando el método createUserWithEmailAndPassword
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // El usuario se ha creado exitosamente
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            if (firebaseUser != null) {
                                // Verifica si el correo electrónico ha sido verificado
                                if (!firebaseUser.isEmailVerified()) {
                                    // Si el correo no ha sido verificado, envía el correo de verificación
                                    firebaseUser.sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> emailTask) {
                                                    if (emailTask.isSuccessful()) {
                                                        // Correo de verificación enviado exitosamente
                                                        Toast.makeText(SignupActivity.this, "Se ha enviado un correo de verificación a " + email, Toast.LENGTH_LONG).show();
                                                    } else {
                                                        // Error al enviar el correo de verificación
                                                        Toast.makeText(SignupActivity.this, "Error al enviar el correo de verificación.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            }

                            // Guarda otros detalles del usuario en la base de datos si es necesario
                            saveUserToDatabase(name, email, username, password);
                        } else {
                            // Si la creación de usuario falla, muestra un mensaje de error
                            Toast.makeText(SignupActivity.this, "Error al crear el usuario: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserToDatabase(String name, String email, String username, String password) {
        // Guarda los detalles del usuario en la base de datos (Firebase Realtime Database)
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        // Crea un nuevo objeto HelperClass con los datos del usuario
        HelperClass helperClass = new HelperClass(name, email, username, password);

        // Establece los datos del usuario en la base de datos Firebase
        reference.child(username).setValue(helperClass);

        // Muestra un mensaje de éxito
        Toast.makeText(SignupActivity.this, "Te has registrado exitosamente!", Toast.LENGTH_SHORT).show();

        // Abre LoginActivity
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}