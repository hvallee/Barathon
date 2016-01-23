package com.example.hvallee.barathon;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hvallee.barathon.DAO.BarsDataSource;
import com.example.hvallee.barathon.Model.Bar;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class BarDetail extends FragmentActivity implements OnMapReadyCallback {

    private TextView vNom;
    private TextView vAdresse;
    private TextView vNum;
    private Bar b;
    private BarsDataSource barSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_detail);

        // Get the extra "id" added through the intent
        long i = getIntent().getLongExtra("id", 0);

        barSource = new BarsDataSource(getBaseContext());
        barSource.open();
        b = barSource.getBarById((int)i);

        // Get and Set the textarea
        vNom = (TextView)findViewById(R.id.NomBarDetail);
        vAdresse = (TextView)findViewById(R.id.AdresseBarDetail);
        vNum = (TextView)findViewById(R.id.NumBarDetail);
        vNom.setText(b.getName());
        vAdresse.setText(b.getAddress());
        vNum.setText(b.getPhone());

        // Get the map and call the getMapAsync, see onMapReady.
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Close the database source
        barSource.close();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(Float.parseFloat(b.getLatitude()), Float.parseFloat(b.getLongitude())))
                .title(b.getName()));
    }
}
