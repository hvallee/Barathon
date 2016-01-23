package com.example.hvallee.barathon;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hvallee.barathon.Adapter.ListBarInsideAdapter;
import com.example.hvallee.barathon.DAO.BarsDataSource;
import com.example.hvallee.barathon.Model.Bar;
import com.example.hvallee.barathon.Model.Parcours;

import java.util.ArrayList;
import java.util.List;

public class ParcoursDetail extends AppCompatActivity {

    private TextView name;
    private TextView description;
    private ListView mListView;
    private ListBarInsideAdapter adapter;
    private BarsDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcours_detail);

        // Masquer l'ActionBar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // On récup les éléments de la vue
        name = (TextView)findViewById(R.id.parcoursNameDetail);
        description = (TextView)findViewById(R.id.parcoursDescriptionDetail);

        // On recup l'id passé en extra dans l'intent
        long i = getIntent().getLongExtra("id", 0);

        // Initialisation de la source de donnée + recup du parcours grace l'id
        dataSource= new BarsDataSource(getBaseContext());
        dataSource.open();
        Parcours p = dataSource.getParcoursById((int) i);

        name.setText(p.getName());
        description.setText(p.getDescription());

        List<Bar> barList = dataSource.getAllBarsOfParcours((int)p.getId());

        mListView = (ListView) findViewById(R.id.parcours_bars_list);
        adapter = new ListBarInsideAdapter(barList, getApplicationContext());
        mListView.setAdapter(adapter);

        // Close du helper sqlite
        dataSource.close();
    }
}
