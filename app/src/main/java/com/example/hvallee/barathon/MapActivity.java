package com.example.hvallee.barathon;

import com.example.hvallee.barathon.DAO.BarsDataSource;
import com.example.hvallee.barathon.Model.Bar;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import java.util.List;

import javax.sql.DataSource;

/**
 * Created by hvallee on 14/10/2015.
 */
public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private BarsDataSource dataSource;
    private static final LatLng RENNES = new LatLng(48.117266,-1.67779);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // Récupération des bars grâce au dataSources
        BarsDataSource  dataSource = new BarsDataSource(this);
        dataSource.open();
        List<Bar> bars = dataSource.getAllBars();
        dataSource.close();

        LatLng pos_bar;
        // map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        // parcours La liste des bars pour mettre les marqueurs
        map.setMyLocationEnabled(true);
        for (Bar b : bars) {

            pos_bar = new LatLng( Double.parseDouble(b.getLatitude()), Double.parseDouble(b.getLongitude()));

            map.addMarker(new MarkerOptions()
                    .title(b.getName())
                    .snippet(b.getAddress())
                    .position(pos_bar));
        }
        // zoom position rennes
        //LatLngBounds RENNES = new LatLngBounds(new LatLng(48, -1.6), new LatLng(48, -1.6));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(RENNES, 12));


    }
}
