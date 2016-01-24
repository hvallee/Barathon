package com.example.hvallee.barathon;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.melnykov.fab.FloatingActionButton;

import com.example.hvallee.barathon.Adapter.ListParcoursAdapter;
import com.example.hvallee.barathon.DAO.BarsDataSource;
import com.example.hvallee.barathon.Model.Parcours;

import java.util.List;

public class ListParcoursActivity extends AppCompatActivity {

    private BarsDataSource dataSource;
    private ListView listView;
    private FloatingActionButton floatAddButton;
    private ListParcoursAdapter adapter;
    private List<Parcours> parcourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_parcours);

        listView = (ListView) findViewById(R.id.listViewParcours);
        floatAddButton = (FloatingActionButton)findViewById(R.id.floatingAddButton);

        // Masquer l'ActionBar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        dataSource = new BarsDataSource(this);
        dataSource.open();
        parcourses = dataSource.getAllParcours();
        dataSource.close();

        adapter = new ListParcoursAdapter(parcourses, this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long idParcours = parcourses.get(position).getId();
                Intent intent = new Intent(getApplication(), ParcoursDetail.class);
                intent.putExtra("id", idParcours);
                startActivity(intent);
            }
        });

        floatAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCreateParcours = new Intent(getApplicationContext(), CreateParcours.class);
                startActivity(intentCreateParcours);
            }
        });

    }

    // Refresh the list by recall the method whom get the parcours list
    public void refresh(){
        dataSource.open();
        parcourses = dataSource.getAllParcours();
        adapter.clear();
        adapter.setmListParcours(parcourses);
        adapter.notifyDataSetChanged();
        dataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }
}
