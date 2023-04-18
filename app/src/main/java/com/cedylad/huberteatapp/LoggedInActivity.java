package com.cedylad.huberteatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LoggedInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        // Récupération du nom d'utilisateur depuis les paramètres de l'application
        String mailU = getSharedPreferences("com.cedylad.huberteatapp.PREFERENCE_FILE_KEY", MODE_PRIVATE)
                .getString("mailU", "");

        // Affichage du message de bienvenue pour l'utilisateur connecté
        getSupportActionBar().setTitle("Bienvenue " + mailU + " !");

        // Récupération du bouton de déconnexion
        Button logoutButton = findViewById(R.id.button_logout);

        // Ajout d'un écouteur d'événements pour l'appui sur le bouton de déconnexion
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Suppression des informations de connexion enregistrées dans les préférences de l'application
                getSharedPreferences("com.cedylad.huberteatapp.PREFERENCE_FILE_KEY", MODE_PRIVATE)
                        .edit()
                        .remove("mailU")
                        .remove("passwordU")
                        .apply();

                // Redirection vers l'activité de connexion
                Intent intent = new Intent(LoggedInActivity.this, MainActivity.class);
                startActivity(intent);

                // Fermeture de l'activité actuelle pour empêcher le retour à la page précédente
                finish();
            }
        });
    }
}
