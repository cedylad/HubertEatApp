package com.cedylad.huberteatapp;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

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
        getSupportActionBar().setTitle("HUberEat | " + firstNameU + " | Solde : " + soldeU + " €");

        LinearLayout layout = findViewById(R.id.commandes_layout);

        for (int i = 0; i < commandes.length(); i++) {
            try {
                //Récupération des attribu depuis le JSON
                JSONObject commande = commandes.getJSONObject(i);
                String idC = commande.getString("idC");
                String dateC = commande.getString("dateC");
                int livraison = commande.getInt("livraison");
                String nomP = commande.getString("nomP");
                String imgP = commande.getString("imgP");

                // Crée une carte pour chaque commande

                LinearLayout carteCommande = new LinearLayout(this);
                carteCommande.setOrientation(LinearLayout.VERTICAL);
                //carteCommande.setBackground(getResources().getDrawable(R.drawable.background_card));

                // Créer les textviews pour chaque propriété

                //Partie icD
                TextView idCTextView = new TextView(this);
                idCTextView.setText("Commande n°" + idC);
                idCTextView.setTextSize(22);
                idCTextView.setTextColor(getResources().getColor(R.color.white));

                //Partie nomP
                TextView nomPTextView = new TextView(this);
                nomPTextView.setText("Plat : " + nomP);
                nomPTextView.setTextSize(16);
                nomPTextView.setTextColor(getResources().getColor(R.color.white));


                //Parti dateC
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
                dateCTextView.setText("Date : " + dateFormatted);
                dateCTextView.setTextSize(16);
                dateCTextView.setTextColor(getResources().getColor(R.color.white));

                //Partie livraison

                TextView livraisonTextView = new TextView(this);
                String livraisonText = "";
                switch (livraison) {
                    case 0:
                        livraisonText += "Commande en attente";
                        carteCommande.setBackground(getResources().getDrawable(R.drawable.background_attente));
                        break;
                    case 1:
                        livraisonText += "Commande livrée";
                        carteCommande.setBackground(getResources().getDrawable(R.drawable.background_valide));
                        break;
                    case 2:
                        livraisonText += "Commande refusée";
                        carteCommande.setBackground(getResources().getDrawable(R.drawable.background_refuse));
                        break;
                    case 3:
                        livraisonText += "Commande annulée";
                        carteCommande.setBackground(getResources().getDrawable(R.drawable.background_annule));
                        break;
                    default:
                        livraisonText += "Commande perdue";
                        carteCommande.setBackground(getResources().getDrawable(R.drawable.background_annule));
                        break;
                }
                livraisonTextView.setText(livraisonText);
                livraisonTextView.setTextSize(30);
                livraisonTextView.setTextColor(getResources().getColor(R.color.white));


                //Partie imgP

                // définit l'image dans l'ImageView
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                Picasso.get().load("https://visumat.fr/img/plat/" + imgP).into(imageView);

                // Ajout les textviews et l'image à la carte de commande
                carteCommande.addView(idCTextView);
                carteCommande.addView(dateCTextView);
                carteCommande.addView(nomPTextView);
                carteCommande.addView(imageView);
                carteCommande.addView(livraisonTextView);

                // Ajout la carte de commande au layout
                layout.addView(carteCommande);

                // Ajout une ligne de séparation
                View separator = new View(this);
                separator.setBackgroundColor(getResources().getColor(R.color.white));
                separator.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 8));
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