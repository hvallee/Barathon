package com.example.hvallee.barathon;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hvallee.barathon.Adapter.ListBarInsideAdapter;
import com.example.hvallee.barathon.DAO.BarsDataSource;
import com.example.hvallee.barathon.Model.Bar;
import com.example.hvallee.barathon.Model.Parcours;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class ParcoursDetail extends FragmentActivity implements OnMapReadyCallback {

    private TextView name;
    private TextView description;
    private ListView mListView;
    private ListBarInsideAdapter adapter;
    private BarsDataSource dataSource;
    private List<Bar> barList;
    private Button launchBarathon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcours_detail);

        // Masquer l'ActionBar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
       /* ActionBar actionBar = getSupportActionBar();
        actionBar.hide();*/

        // On récup les éléments de la vue
        name = (TextView)findViewById(R.id.parcoursNameDetail);
        description = (TextView)findViewById(R.id.parcoursDescriptionDetail);
        launchBarathon = (Button)findViewById(R.id.launchBarathon);

        // On recup l'id passé en extra dans l'intent
        long i = getIntent().getLongExtra("id", 0);
        // Initialisation de la source de donnée + recup du parcours grace l'id
        dataSource= new BarsDataSource(getBaseContext());
        dataSource.open();
        Parcours p = dataSource.getParcoursById((int) i);

        name.setText(p.getName());
        description.setText(p.getDescription());

        barList = dataSource.getAllBarsOfParcours((int)p.getId());

        mListView = (ListView) findViewById(R.id.parcours_bars_list);
        adapter = new ListBarInsideAdapter(barList, getApplicationContext(), (int)i);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "élément cliqué : " + barList.get(position).getId(), Toast.LENGTH_SHORT).show();
                    /*
                    //On recup l'id de l'élément cliqué
                    long idBar = barList.get(position).getId();
                    // Creation d'un intent avec en extra l'id, puis start detail activity
                    Intent intentbardetail = new Intent(getApplication(), BarDetail.class);
                    intentbardetail.putExtra("id", idBar);
                    startActivity(intentbardetail);
                    */
            }
        });

        if (barList.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Il n'y a rien ici ! Rendez-vous sur la liste des bars pour en ajouter.", Toast.LENGTH_LONG).show();
        }

        launchBarathon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), ParcoursLaunch.class);
                intent.putExtra("id", getIntent().getLongExtra("id", 0));
                startActivity(intent);
            }
        });
/*
        // Get the map and call the getMapAsync, see onMapReady.
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
        // Close du helper sqlite
        dataSource.close();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(" test ");
        menu.add(0, v.getId(), 0, "test");//groupId, itemId, order, title
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        boolean first = true;
        PolylineOptions line = new PolylineOptions();
        line.color(Color.RED);
        for(Bar b: barList) {
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Float.parseFloat(b.getLatitude()), Float.parseFloat(b.getLongitude())))
                    .title(b.getName()));
            line.add(new LatLng(Float.parseFloat(b.getLatitude()), Float.parseFloat(b.getLongitude())));

            if(first) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Float.parseFloat(b.getLatitude()), Float.parseFloat(b.getLongitude())), 15));
                // Zoom in, animating the camera.
                googleMap.animateCamera(CameraUpdateFactory.zoomIn());
                // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(12), 1000, null);
                first = false;
            }

        }
        googleMap.addPolyline(line);
    }

}
