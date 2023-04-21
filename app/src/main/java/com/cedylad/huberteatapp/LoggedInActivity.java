package com.cedylad.huberteatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

public class LoggedInActivity extends AppCompatActivity {

    private String mailU;
    private String firstNameU;
    private String soldeU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        // Récupération du nom d'utilisateur depuis les paramètres de l'application
        mailU = getIntent().getStringExtra("mailU");
        firstNameU = getIntent().getStringExtra("firstNameU");
        soldeU = getIntent().getStringExtra("soldeU");

        // Récupération de la JSONArray des commandes depuis les paramètres de l'application
        JSONArray commandes = null;
        try {
            commandes = new JSONArray(getIntent().getStringExtra("commandes"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Affichage du message de bienvenue pour l'utilisateur connecté
        getSupportActionBar().setTitle("HUberEat | " + firstNameU + " | Solde :" + soldeU + " €");



// Affichage des informations des commandes dans un TextView
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView dateTexteView = findViewById(R.id.dateTexteView);
        TextView etatTextView = findViewById(R.id.etatTextView);
        StringBuilder commandeTexte = new StringBuilder();
        for(int i = 0; i < commandes.length(); i++) {

               // String commande = "Commande n°" + commandes.getJSONObject(i).getString("idC") + " commandé le " + commandes.getJSONObject(i).getString("dateC") + " avec livraison " + commandes.getJSONObject(i).getString("livraison");
               // commandeTexte.append(commande).append("\n");

        }
        titleTextView.setText(commandeTexte.toString());



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
