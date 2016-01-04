package com.example.hvallee.barathon;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.baoyz.widget.PullRefreshLayout;
import com.example.hvallee.barathon.Adapter.ListBarAdapter;
import com.example.hvallee.barathon.DAO.BarsDataSource;
import com.example.hvallee.barathon.Model.Bar;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import barathon.backend.myApi.MyApi;

/**
 * Created by yoannlt on 04/01/2016.
 */
public class EndpointsAsyncTaskFetchBars extends AsyncTask<Void, Void, String> {

    private MyApi myApiService = null;
    private BarsDataSource datasource;
    private Context mContext;
    private ListBarAdapter mAdapter;
    private PullRefreshLayout swipeRefresh;

    public EndpointsAsyncTaskFetchBars(BarsDataSource datasource, Context mContext, ListBarAdapter adapter, PullRefreshLayout swipeRefresh) {
        this.datasource = datasource;
        this.mContext = mContext;
        mAdapter = adapter;
        this.swipeRefresh = swipeRefresh;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        datasource = new BarsDataSource(mContext);
        datasource.open();
    }

    @Override
    protected String doInBackground(Void... params) {

        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.3.2:8080/_ah/api/")
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
        mAdapter.clear();
        mAdapter.setmListBars(datasource.getAllBars());
        mAdapter.notifyDataSetChanged();

        swipeRefresh.setRefreshing(false);
        datasource.close();
    }

    private void updateDatabase(String response) {
        Log.d("Reponse server :", response);

        try{
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
                Bar bar = datasource.getBarByName(name);

                // Si la requete ne donne rien, on creer le bar
                if (bar == null) {
                    Bar bar2 = datasource.createBar(name, address, "",latitude, longitude);
                    Log.d("bar created : ", bar2.toString());
                }
            }
        } catch (Exception e) {
            Log.d("updateDatabase() : ", e.getMessage());
        }
    }
}
