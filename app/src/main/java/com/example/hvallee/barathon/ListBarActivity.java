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

public class ListBarActivity extends AppCompatActivity implements OnTaskCompleted {

    private BarsDataSource dataSource;
    private ListView listView;
    private ListBarAdapter adapter;
    private List<Bar> bars;
    private PullRefreshLayout swipeRefresh;
    private EndpointsAsyncTaskFetchBars asyncTask;

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

        asyncTask = new EndpointsAsyncTaskFetchBars(this, this);

        swipeRefresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                asyncTask.execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Callback lorsque l'asynctask est terminée
    @Override
    public void OnTaskCompleted() {
        // Update de la liste et de l'adapter + notification à l'adapter que les données ont été modifiées
        dataSource.open();
        bars = dataSource.getAllBars();
        adapter.clear();
        adapter.setmListBars(bars);
        adapter.notifyDataSetChanged();
        dataSource.close();

        // Arrêt de l'animation de refresh
        swipeRefresh.setRefreshing(false);
    }
}
