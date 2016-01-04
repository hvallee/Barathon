package com.example.hvallee.barathon;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.hvallee.barathon.DAO.BarsDataSource;
import com.example.hvallee.barathon.Model.Bar;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;


import barathon.backend.myApi.MyApi;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    private BarsDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataSource = new BarsDataSource(getApplication());
        dataSource.open();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_listeBars) {
            Intent intent = new Intent(getApplicationContext(), ListBarActivity.class);
            startActivity(intent);
        }
        if(id == R.id.action_refresh_data) {
            new EndpointsAsyncTaskFetchBars(getApplication()).execute();
        }

        return super.onOptionsItemSelected(item);
    }

    public void showMap(View view) {

        // creation d'un intent pour appeler une autre activité (SecondaryActivity)
        Intent intent = new Intent(getApplicationContext(), MapActivity.class);

        //lancement de la seconde activité
        startActivity(intent);
    }

    public void showList(View view) {
        Intent intent = new Intent(getApplicationContext(), ListBarActivity.class);
        startActivity(intent);
    }
}