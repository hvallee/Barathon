package com.example.hvallee.barathon;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class ParcoursLaunch extends FragmentActivity implements OnMapReadyCallback {

    private TextView name;
    private TextView etape;
    private TextView currentBar;
   /* private ListView mListView;*/
    private ListBarInsideAdapter adapter;
    private BarsDataSource dataSource;
    private List<Bar> barList;
    private int currentStep = 1;
    private int maxStep;
    private Button nextStep;
    private Button previousStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcours_launch);

        // Masquer l'ActionBar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
       /* ActionBar actionBar = getSupportActionBar();
        actionBar.hide();*/

        // On récup les éléments de la vue
        name = (TextView)findViewById(R.id.parcoursNameDetail);
        etape = (TextView)findViewById(R.id.parcoursEtape);
        currentBar = (TextView)findViewById(R.id.currentBar);
        previousStep = (Button)findViewById(R.id.previousStep);
        nextStep = (Button)findViewById(R.id.nextStep);

        // On recup l'id passé en extra dans l'intent
        long i = getIntent().getLongExtra("id", 0);
        // Initialisation de la source de donnée + recup du parcours grace l'id
        dataSource= new BarsDataSource(getBaseContext());
        dataSource.open();
        Parcours p = dataSource.getParcoursById((int) i);

        name.setText(p.getName());


        barList = dataSource.getAllBarsOfParcours((int)p.getId());
        adapter = new ListBarInsideAdapter(barList, getApplicationContext(), (int)i);
        maxStep = barList.size();
        etape.setText(currentStep+"/"+maxStep);
        currentBar.setText(barList.get(currentStep-1).getName());
        adapter = new ListBarInsideAdapter(barList, getApplicationContext(), (int)i);

     //   });

        // Get the map and call the getMapAsync, see onMapReady.
        final MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        previousStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(currentStep > 1) {
                   currentStep--;
                   etape.setText(currentStep+"/"+maxStep);
                   currentBar.setText(barList.get(currentStep - 1).getName());
                   generateMap(mapFragment.getMap());

               }
            }
        });

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStep < maxStep) {
                    currentStep++;
                    etape.setText(currentStep + "/" + maxStep);
                    currentBar.setText(barList.get(currentStep - 1).getName());
                    generateMap(mapFragment.getMap());
                }
                else if(currentStep == maxStep) {
                    new AlertDialog.Builder(ParcoursLaunch.this)
                            .setTitle("Barathon terminé !")
                            .setMessage("Félicitations ! Vous avez terminé ce barathon ! N'hésitez pas à prendre une photo souvenir :)")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                    startActivityForResult(intent, 0);
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });


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
        generateMap(googleMap);
    }

    public void generateMap(GoogleMap googleMap) {
        googleMap.clear();
        boolean first = true;
        PolylineOptions line = new PolylineOptions();
        line.color(Color.RED);
        int i = 0;
        for (Bar b: barList) {
            if(i < currentStep-1) {
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Float.parseFloat(b.getLatitude()), Float.parseFloat(b.getLongitude())))
                        .title(b.getName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
            else if(i > currentStep-1) {
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Float.parseFloat(b.getLatitude()), Float.parseFloat(b.getLongitude())))
                        .title(b.getName()));
            }
            else {
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Float.parseFloat(b.getLatitude()), Float.parseFloat(b.getLongitude())))
                        .title(b.getName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            }

            line.add(new LatLng(Float.parseFloat(b.getLatitude()), Float.parseFloat(b.getLongitude())));

            if(first) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Float.parseFloat(b.getLatitude()), Float.parseFloat(b.getLongitude())), 15));
                // Zoom in, animating the camera.
                googleMap.animateCamera(CameraUpdateFactory.zoomIn());
                // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(14), 1000, null);
                first = false;
            }
            i++;
        }
        googleMap.addPolyline(line);
    }

}
