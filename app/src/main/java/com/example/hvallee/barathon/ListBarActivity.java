package com.example.hvallee.barathon;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hvallee.barathon.Adapter.ListBarAdapter;
import com.example.hvallee.barathon.DAO.BarsDataSource;
import com.example.hvallee.barathon.Model.Bar;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.List;

import barathon.backend.myApi.MyApi;

public class ListBarActivity extends ListActivity {

    private ListBarAdapter adapter;
    private List<Bar> bars;
    private BarsDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // We'll define a custom screen layout here (the one shown above), but
        // typically, you could just use the standard ListActivity layout.
        setContentView(R.layout.activity_list_bar);

        dataSource = new BarsDataSource(this);
        dataSource.open();
        bars = dataSource.getAllBars();
        dataSource.close();

        adapter = new ListBarAdapter(bars, this);

        // Bind to our new adapter
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Bar item = bars.get(position);
        long idBar = bars.get(position).getId();
       // Toast.makeText(this, item.getName() + " selected", Toast.LENGTH_LONG).show();
        // creer intent pour ouvrir une nouvelle activity
        Toast.makeText(getApplication(),"onListItemClick(), po de soucis",Toast.LENGTH_LONG).show();
        Intent intentbardetail = new Intent(getApplication(), BarDetails.class);
        intentbardetail.putExtra("id",idBar);
        startActivity(intentbardetail);

    }
}
