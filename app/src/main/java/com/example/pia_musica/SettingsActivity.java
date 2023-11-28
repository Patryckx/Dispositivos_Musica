package com.example.pia_musica;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private ListView listView;
    private SongAdapter songAdapter;
    private List<Song> songList;

    // Referencia a la base de datos de Firebase
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // Inicializar la lista de canciones y el adaptador
        songList = new ArrayList<>();
        songAdapter = new SongAdapter(this, songList);

        // Obtener referencias a las vistas
        listView = findViewById(R.id.listView);

        // Configurar el adaptador en la lista
        listView.setAdapter(songAdapter);

        // Obtener referencia a la base de datos de Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("songs");

        // Agregar un listener para escuchar los cambios en la base de datos
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Limpiar la lista actual de canciones
                songList.clear();

                // Iterar sobre los datos obtenidos de Firebase
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Obtener la canción actual y agregarla a la lista
                    Song song = snapshot.getValue(Song.class);
                    songList.add(song);
                }

                // Notificar al adaptador que los datos han cambiado
                songAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar errores de lectura de la base de datos (opcional)
            }
        });

        //Condicionales del menu
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_home) {
                return true;
            } else if (item.getItemId() == R.id.bottom_search) {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.bottom_settings) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
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
    }
}