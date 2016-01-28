package com.example.hvallee.barathon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.example.hvallee.barathon.Adapter.ListBarAdapter;
import com.example.hvallee.barathon.DAO.BarsDataSource;
import com.example.hvallee.barathon.Model.Bar;
import com.example.hvallee.barathon.Model.Parcours;

import java.util.List;

public class ListBarActivity extends AppCompatActivity implements OnTaskCompleted {

    private final static String LOG_TAG = ListBarActivity.class.getSimpleName();

    private BarsDataSource dataSource;
    private ListView listView;
    private EditText searchBox;
    private ListBarAdapter adapter;
    private List<Bar> bars;
    private List<Parcours> parcourses;
    private PullRefreshLayout swipeRefresh;

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
        parcourses = dataSource.getAllParcours();
        dataSource.close();
        if (parcourses.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Pour charger les bars, faite un mouvement de haut en bas avec votre index, puis relâchez", Toast.LENGTH_LONG).show();
        }
        adapter = new ListBarAdapter(bars, this);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        // Action d'un click sur la listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //On recup l'id de l'élément cliqué
                long idBar = bars.get(position).getId();
                // Creation d'un intent avec en extra l'id, puis start detail activity
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
                if (searchBox.getText().toString().equals("")) {
                    bars = dataSource.getAllBars();
                } else {
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Ajouter le bar à un barathon");

        for(Parcours p : parcourses){
            menu.add(0, v.getId(), 0, p.getName());//groupId, itemId, order, title
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int index = info.position;

        Bar bar = adapter.getItem(index);

        dataSource.open();

        for(Parcours p : parcourses){
            if(item.getTitle().toString().equals(p.getName())) {
                Long id = dataSource.insertBarIntoParcours((int)p.getId(),(int)bar.getId());
                Log.d(LOG_TAG, "Insertion bar: " + index + " into parcours: " + p.getId() + " with id: " + id);
            }
        }

        dataSource.close();

        return super.onContextItemSelected(item);
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
