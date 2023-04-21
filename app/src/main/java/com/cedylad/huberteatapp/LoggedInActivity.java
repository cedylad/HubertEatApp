package com.cedylad.huberteatapp;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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

        LinearLayout layout = findViewById(R.id.commandes_layout);

        for (int i = 0; i < commandes.length(); i++) {
            try {
                JSONObject commande = commandes.getJSONObject(i);
                String idC = commande.getString("idC");
                String dateC = commande.getString("dateC");
                int livraison = commande.getInt("livraison");

                // Créer les textviews pour chaque propriété
                TextView idCTextView = new TextView(this);
                idCTextView.setText("Commande n°" + idC);

                // Convertir la date au format "jour/mois/année"
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateFormatted = "";
                try {
                    dateFormatted = outputFormat.format(inputFormat.parse(dateC));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                TextView dateCTextView = new TextView(this);
                dateCTextView.setText("Data : " + dateFormatted);

                TextView livraisonTextView = new TextView(this);
                String livraisonText = "Etat de la livraison : ";
                switch(livraison) {
                    case 0:
                        livraisonText += "Commande en attente";
                        break;
                    case 1:
                        livraisonText += "Commande livrée";
                        break;
                    case 2:
                        livraisonText += "Refusée";
                        break;
                    case 3:
                        livraisonText += "Annulée";
                        break;
                    default:
                        livraisonText += "Commande perdu";
                        break;
                }
                livraisonTextView.setText(livraisonText);

                // Ajouter les textviews au layout
                layout.addView(idCTextView);
                layout.addView(dateCTextView);
                layout.addView(livraisonTextView);

                // Ajouter une ligne de séparation
                View separator = new View(this);
                separator.setBackgroundColor(getResources().getColor(R.color.black));
                separator.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
                layout.addView(separator);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


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
