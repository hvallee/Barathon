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
            new EndpointsAsyncTask().execute();
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

    private class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
        private MyApi myApiService = null;
        //private Context context;

        @Override
        protected String doInBackground(Void... params) {
            if(myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                myApiService = builder.build();
            }

            try {
                return myApiService.bars().execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            updateDatabase(result);
        }
    }

    /*
    String data : les données des bars au format JSON
     */
    private void updateDatabase(String response) {
        Log.d("Reponse server :", response);

        try{
            //List<Bar> bars = new ArrayList<>();
            JSONObject formattedData = new JSONObject(response);
            JSONArray arr = formattedData.getJSONArray("results");

            for (int i = 0; i < arr.length(); i++)
            {
                String name = arr.getJSONObject(i).getString("name");
                String address = arr.getJSONObject(i).getString("address");
                String phone = arr.getJSONObject(i).getString("phone");
                String latitude = arr.getJSONObject(i).getString("latitude");
                String longitude = arr.getJSONObject(i).getString("longitude");

                // On fait une requete sur le nom du bar
                Bar bar = dataSource.getBarByName(name);


                // Si la requete ne donne rien, on creer le bar
                if (bar == null) {
                    Bar bar2 = dataSource.createBar(name, address, "",latitude, longitude);
                    Log.d("bar created : ", bar2.toString());
                }

            }
        } catch (Exception e) {
            Log.d("updateDatabase() : ", e.getMessage());
        }
    }
}