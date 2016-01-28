package com.example.hvallee.barathon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.hvallee.barathon.Model.Bar;
import com.melnykov.fab.FloatingActionButton;

import com.example.hvallee.barathon.Adapter.ListParcoursAdapter;
import com.example.hvallee.barathon.DAO.BarsDataSource;
import com.example.hvallee.barathon.Model.Parcours;

import java.util.List;

public class ListParcoursActivity extends AppCompatActivity {

    private BarsDataSource dataSource;
    private ListView listView;
    private Button floatAddButton;
    private ListParcoursAdapter adapter;
    private List<Parcours> parcourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_parcours);

        listView = (ListView) findViewById(R.id.listViewParcours);
        floatAddButton = (Button)findViewById(R.id.addButton);

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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //On recup l'id de l'élément cliqué
                final long idParcours = parcourses.get(position).getId();
                new AlertDialog.Builder(ListParcoursActivity.this)
                        .setTitle("Suppression")
                        .setMessage("Voulez-vous vraiment supprimer ce Barathon?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Parcours parcours = parcourses.get(position);
                                dataSource.open();
                                dataSource.deleteParcours(idParcours);
                                dataSource.close();
                                refresh();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return false;
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
