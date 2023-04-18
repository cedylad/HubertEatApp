package com.cedylad.huberteatapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;

    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this; //ajout de cette ligne pour initialiser la variable "activity"

        editTextUsername = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonLogin = findViewById(R.id.button_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                MySQLConnection mysqlConnection = new MySQLConnection();
                mysqlConnection.execute(username, password);
            }
        });
    }

    public void loginSuccess() {
        Toast.makeText(getApplicationContext(), "Connexion réussie", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, LoggedInActivity.class);
        startActivity(intent);
    }

    public void loginError() {
        Toast.makeText(getApplicationContext(), "Adresse e-mail ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
    }
}
