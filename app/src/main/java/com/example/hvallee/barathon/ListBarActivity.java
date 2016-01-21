package com.example.hvallee.barathon;

//import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.baoyz.widget.PullRefreshLayout;
import com.example.hvallee.barathon.Adapter.ListBarAdapter;
import com.example.hvallee.barathon.DAO.BarsDataSource;
import com.example.hvallee.barathon.Model.Bar;

import java.util.List;

public class ListBarActivity extends AppCompatActivity implements OnTaskCompleted {

    private BarsDataSource dataSource;
    private ListView listView;
    private EditText searchBox;
    private ListBarAdapter adapter;
    private List<Bar> bars;
    private PullRefreshLayout swipeRefresh;
    private EndpointsAsyncTaskFetchBars asyncTask;

    private Context context;
    private OnTaskCompleted taskCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bar);

        // Masquer l'ActionBar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

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
                Intent intentbardetail = new Intent(getApplication(), BarDetail.class);
                intentbardetail.putExtra("id", idBar);
                startActivity(intentbardetail);
            }
        });

        // A mettre en paramètre pour le callback
        context = this;
        taskCompleted = this;
        swipeRefresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new EndpointsAsyncTaskFetchBars(taskCompleted, context).execute();
            }
        });

        searchBox = (EditText)findViewById(R.id.searchBox);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dataSource.open();
                if(searchBox.getText().toString().equals("")) {
                    bars = dataSource.getAllBars();
                }
                else {
                    bars = dataSource.searchBarByString(searchBox.getText().toString());
                }
                adapter.clear();
                adapter.setmListBars(bars);
                adapter.notifyDataSetChanged();
                dataSource.close();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

                /*
                setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                dataSource.open();
                if(searchBox.getText().toString().equals("")) {
                    bars = dataSource.getAllBars();
                }
                else {
                    bars = dataSource.searchBarByString(searchBox.getText().toString());
                }
                adapter.clear();
                adapter.setmListBars(bars);
                adapter.notifyDataSetChanged();
                dataSource.close();
                return false;
            }
        });*/
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
