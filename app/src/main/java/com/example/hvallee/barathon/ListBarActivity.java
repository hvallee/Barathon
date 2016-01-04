package com.example.hvallee.barathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baoyz.widget.PullRefreshLayout;
import com.example.hvallee.barathon.Adapter.ListBarAdapter;
import com.example.hvallee.barathon.DAO.BarsDataSource;
import com.example.hvallee.barathon.Model.Bar;

import java.util.List;

public class ListBarActivity extends AppCompatActivity {


    private BarsDataSource dataSource;
    private ListView listView;
    private ListBarAdapter adapter;
    private List<Bar> bars;
    private PullRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bar);

        listView = (ListView) findViewById(R.id.listView1);
        swipeRefresh = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        dataSource = new BarsDataSource(this);
        dataSource.open();
        bars = dataSource.getAllBars();
        dataSource.close();

        adapter = new ListBarAdapter(bars, this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long idBar = bars.get(position).getId();
                Intent intentbardetail = new Intent(getApplication(), BarDetails.class);
                intentbardetail.putExtra("id", idBar);
                startActivity(intentbardetail);
            }
        });

        swipeRefresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new EndpointsAsyncTaskFetchBars(dataSource, getApplication(), adapter, swipeRefresh).execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
/*
    private class EndpointsAsyncTaskFetchBarsTest extends AsyncTask<Void, Void, String> {

        private MyApi myApiService = null;
        //private BarsDataSource datasource;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //datasource = new BarsDataSource(getApplication());
            dataSource.open();
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

            adapter.clear();
            adapter.setmListBars(dataSource.getAllBars());
            adapter.notifyDataSetChanged();

            swipeRefresh.setRefreshing(false);
            dataSource.close();
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
    */
}
