package com.example.pia_musica;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class MainActivity extends AppCompatActivity {
    EditText nombreCancionEditText, bandaEditText, anoEditText;
    Button guardarButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_home) {
                return true;
            } else if (item.getItemId() == R.id.bottom_search) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.bottom_settings) {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.bottom_profile) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });

        nombreCancionEditText = findViewById(R.id.nombre_cancion_edittext);
        bandaEditText = findViewById(R.id.banda_edittext);
        anoEditText = findViewById(R.id.ano_edittext);
        guardarButton = findViewById(R.id.guardar_button);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("songs");

        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreCancion = nombreCancionEditText.getText().toString();
                String banda = bandaEditText.getText().toString();
                int ano = Integer.parseInt(anoEditText.getText().toString());

                Song song = new Song(nombreCancion, banda, ano);
                reference.push().setValue(song);

                Toast.makeText(MainActivity.this, "Canción guardada exitosamente", Toast.LENGTH_SHORT).show();

                nombreCancionEditText.setText("");
                bandaEditText.setText("");
                anoEditText.setText("");
            }
        });
    }


    }
