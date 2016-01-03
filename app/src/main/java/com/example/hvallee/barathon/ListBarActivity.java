package com.example.hvallee.barathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hvallee.barathon.Adapter.ListBarAdapter;
import com.example.hvallee.barathon.DAO.BarsDataSource;
import com.example.hvallee.barathon.Model.Bar;

import java.util.List;

public class ListBarActivity extends AppCompatActivity {

    private List<Bar> bars;
    private BarsDataSource dataSource;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_list_bar_activity);

        listView = (ListView) findViewById(R.id.listView1);

        dataSource = new BarsDataSource(this);
        dataSource.open();
        bars = dataSource.getAllBars();
        dataSource.close();

        listView.setAdapter(new ListBarAdapter(bars, this));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long idBar = bars.get(position).getId();
                Toast.makeText(getApplication(),"onListItemClick(), po de soucis",Toast.LENGTH_LONG).show();
                Intent intentbardetail = new Intent(getApplication(), BarDetails.class);
                intentbardetail.putExtra("id",idBar);
                startActivity(intentbardetail);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
