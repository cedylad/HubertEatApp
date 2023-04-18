package com.cedylad.huberteatapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class LoggedInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        // Récupération du nom d'utilisateur depuis les paramètres de l'application
        String nameU = getSharedPreferences("com.cedylad.huberteatapp.PREFERENCE_FILE_KEY", MODE_PRIVATE)
                .getString("nameU", "");

        // Affichage du message de bienvenue pour l'utilisateur connecté
        getSupportActionBar().setTitle("Bienvenue " + nameU + " !");
    }
}
