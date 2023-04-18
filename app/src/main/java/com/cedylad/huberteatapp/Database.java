package com.cedylad.huberteatapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Database {
    private Context context;

    public RequestQueue queue;

    public Database(Context context){
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }
}

