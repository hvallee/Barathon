package com.example.hvallee.barathon;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.provider.Contacts;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hvallee.barathon.Adapter.ListBarAdapter;
import com.example.hvallee.barathon.DAO.BarsDataSource;
import com.example.hvallee.barathon.Model.Bar;

import java.util.ArrayList;
import java.util.List;

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
        Toast.makeText(this, item.getName() + " selected", Toast.LENGTH_LONG).show();
    }
}
