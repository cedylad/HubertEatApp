package com.cedylad.huberteatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText mailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    private String mailU;
    private String passwordU;
    private String firstNameU;
    private String soldeU;
    private Database database;
    private JSONArray commandes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mailEditText = findViewById(R.id.mailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        database = new Database(getApplicationContext());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mailU = mailEditText.getText().toString();
                passwordU = passwordEditText.getText().toString();

                connectUser(); //Lance la requete pour connecter l'utilisateur
            }
        });
    }

    public void onApiResponse(JSONObject response) {
        Boolean success = null;
        String error = "";

        try {
            success = response.getBoolean("success");

            if(success == true) {
                firstNameU = response.getString("firstNameU");
                soldeU = response.getString("soldeU");
                commandes = response.getJSONArray("commandes");

                Intent loggedInActivity = new Intent(getApplicationContext(), LoggedInActivity.class);
                loggedInActivity.putExtra("mailU", mailU);
                loggedInActivity.putExtra("firstNameU", firstNameU);
                loggedInActivity.putExtra("soldeU", soldeU);
                loggedInActivity.putExtra("commandes", commandes.toString());
                startActivity(loggedInActivity);
                finish();


            } else {
                error = response.getString("error");
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }


    public void connectUser() {
        String url = "https://visumat.fr/api.php";

        Map<String, String> params = new HashMap<>();
        params.put("mailU", mailU);
        params.put("passwordU", passwordU);

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                onApiResponse(response);
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

        database.queue.add(jsonObjectRequest);

    }
}
