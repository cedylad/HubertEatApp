package com.cedylad.huberteatapp;

import android.os.AsyncTask;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection extends AsyncTask<String, Void, Boolean> {

    private static final String HOST = "localhost";
    private static final String PORT = "3306"; // Port de connexion de MySQL dans MAMP
    private static final String DB_NAME = "hubereat";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    @Override
    protected Boolean doInBackground(String... strings) {
        String mailU = strings[0];
        String passwordU = strings[1];

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE mailU = '" + mailU + "' AND passwordU = '" + passwordU + "'");
            if (resultSet.next()) {
                return true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);

        MainActivity mainActivity = (MainActivity) MainActivity.activity;
        if (success) {
            mainActivity.loginSuccess();
        } else {
            mainActivity.loginError();
        }
    }
}
